//
//  PlaylistDetailScreen.swift
//  Ios
//
//  Created by Ashu Tyagi on 14/01/24.
//

import Foundation
import SwiftUI
import shared

struct PlaylistDetailScreen : View {
    
    var playlisId: String
    
    init(playlisId: String) {
        self.playlisId = playlisId
    }
    
    @State
    private var viewModel: PlaylistDetailViewModel?
    
    @State
    private var items: [PlaylistDetailItem] = []
    
    @State
    private var hasNextPage: Bool = true
    
    @State
    private var errorMessage: String? = nil
    
    @State 
    private var showLoadingPlaceholder: Bool = false
    
    private var delegate = SwiftUiPagingHelper<PlaylistDetailItem>()
    
    var body: some View {
        VStack(alignment: .center, spacing: 0) {
            if(!items.isEmpty) {
                List {
                    ForEach(items, id: \.id) { item in
                        getPlaylistDetailItemView(playlistDetailItem: item)
                    }
                    if(hasNextPage && errorMessage == nil) {
                        nextPageLoadingView
                    }
                    if(errorMessage != nil) {
                        nextPageErrorView
                    }
                }
                .listStyle(.plain)
                .background(Color.black.opacity(0.92))
            } else if(showLoadingPlaceholder) {
                initialLoadingView
            } else if(errorMessage != nil && items.isEmpty) {
                nextPageErrorView
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.black.opacity(0.92))
        .task {
            let viewModel = SharedModuleDependencies.shared.playlistDetailViewModel
            await withTaskCancellationHandler(
                operation: {
                    self.viewModel = viewModel
                    Task {
                        try? await viewModel.fetchPlaylistDetail(playlistId: playlisId)
                    }
                    for await pagingData in viewModel.pagingData {
                        try? await skie(delegate).submitData(pagingData: pagingData)
                        
                    }
                },
                onCancel: {
                    viewModel.clear()
                    self.viewModel = nil
                }
            )
        }
        .task {
            for await _ in delegate.onPagesUpdatedFlow {
                self.items = delegate.getItems()
            }
        }
        .task {
            for await loadState in delegate.loadStateFlow {
                switch onEnum(of: loadState.append) {
                case .error(let errorState):
                    self.errorMessage = errorState.error.message
                    break
                case .loading(let loadingState):
                    self.hasNextPage = !loadingState.endOfPaginationReached
                    break
                case .notLoading(_):
                    break
                }
                
                switch onEnum(of: loadState.refresh) {
                case .error(let errorState):
                    self.errorMessage = errorState.error.message
                    self.showLoadingPlaceholder = false
                    break
                case .loading(_):
                    self.showLoadingPlaceholder = true
                    break
                case .notLoading(_):
                    self.showLoadingPlaceholder = false
                    break
                }
            }
        }
    }
    
    private var nextPageLoadingView: some View {
        HStack {
            Spacer()
            ListLoadingItem()
            Spacer()
        }
        .listRowInsets(EdgeInsets())
        .listRowBackground(Color.clear)
        .onAppear{
            delegate.loadNextPage()
            UITableView.appearance().separatorColor = .clear
        }
    }
    
    private var initialLoadingView: some View {
        HStack {
            Spacer()
            ListLoadingItem()
            Spacer()
        }
        .listRowInsets(EdgeInsets())
        .listRowBackground(Color.clear)
        .onAppear{
            UITableView.appearance().separatorColor = .clear
        }
    }
    
    private var nextPageErrorView: some View {
        HStack {
            ListErrorItem(
                errorMessage: errorMessage!,
                onRetryClicked: {
                    delegate.retry()
                    self.errorMessage = nil
                }
            )
        }
        .listRowInsets(EdgeInsets())
        .listRowBackground(Color.clear)
        .onAppear{
            UITableView.appearance().separatorColor = .clear
        }
    }
    
}

@ViewBuilder
private func getPlaylistDetailItemView(playlistDetailItem: PlaylistDetailItem) -> some View {
    HStack(spacing: 0) {
        Text(playlistDetailItem.trackName)
            .foregroundColor(Color.white)
            .padding(.all, 20)
            .listRowInsets(EdgeInsets())
            .listRowBackground(Color.clear)
            .onAppear{
                UITableView.appearance().separatorColor = .clear
            }
    }
}

#Preview {
    PlaylistDetailScreen(
        playlisId: ""
    )
}
