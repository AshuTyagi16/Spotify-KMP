//
//  AlbumDetailScreen.swift
//  Ios
//
//  Created by Ashu Tyagi on 16/01/24.
//

import Foundation
import SwiftUI
import Kingfisher
import FlowStacks
import shared

struct AlbumDetailScreen : View {
    
    private var albumItem: AlbumItem
    
    init(albumItem: AlbumItem) {
        self.albumItem = albumItem
    }
    
    
    @EnvironmentObject private var navigator: FlowNavigator<AppRoute>
    
    @State
    private var viewModel: AlbumDetailViewModel?
    
    @State
    private var items: [AlbumDetailItem] = []
    
    @State
    private var hasNextPage: Bool = true
    
    @State
    private var errorMessage: String? = nil
    
    @State
    private var showLoadingPlaceholder: Bool = false
    
    private let pagingHelper = SwiftUiPagingHelper<AlbumDetailItem>()
    
    var body: some View {
        VStack(alignment: .center, spacing: 0) {
            HStack {
                Image(systemName: "arrow.left")
                    .frame(width: 16, height: 16)
                    .onTapGesture {
                        navigator.goBack()
                    }
                
                Text(albumItem.name)
                    .foregroundColor(Color.white)
                    .font(.system(size:16, weight: .semibold))
                    .padding(.horizontal, 12)
                Spacer()
            }
            .padding(.all, 12)
            
            if(items.isEmpty){
                Spacer()
            }
            if(!items.isEmpty) {
                List {
                    KFImage(URL(string: albumItem.image))
                        .resizable()
                        .frame(height: 400)
                        .scaledToFit()
                        .listRowInsets(EdgeInsets())
                        .listRowBackground(Color.clear)
                    
                    ForEach(items, id: \.id) { item in
                        getAlbumDetailItemView(albumItem: self.albumItem, albumDetailItem: item)
                    }
                    if(hasNextPage && errorMessage == nil) {
                        getLoadindView()
                            .onAppear{
                                pagingHelper.loadNextPage()
                            }
                    }
                    if(errorMessage != nil) {
                        getErrorView(
                            errorMessage: errorMessage!,
                            onRetryClicked: {
                                pagingHelper.retry()
                                self.errorMessage = nil
                            }
                        )
                    }
                }
                .listStyle(.plain)
                .background(Color.black.opacity(0.92))
            } else if(showLoadingPlaceholder) {
                getLoadindView()
            } else if(errorMessage != nil && items.isEmpty) {
                getErrorView(
                    errorMessage: errorMessage!,
                    onRetryClicked: {
                        pagingHelper.retry()
                        self.errorMessage = nil
                    }
                )
            }
            if(items.isEmpty){
                Spacer()
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .background(Color.black.opacity(0.92))
        .task {
            let viewModel = SharedModuleDependencies.shared.albumDetailViewModel
            await withTaskCancellationHandler(
                operation: {
                    self.viewModel = viewModel
                    Task {
                        try? await viewModel.fetchAlbumDetail(albumId:albumItem.id)
                    }
                    for await pagingData in viewModel.pagingData {
                        try? await skie(pagingHelper).submitData(pagingData: pagingData)
                        
                    }
                },
                onCancel: {
                    viewModel.clear()
                    DispatchQueue.main.async {
                        self.viewModel = nil
                    }
                    
                }
            )
        }
        .task {
            for await _ in pagingHelper.onPagesUpdatedFlow {
                self.items = pagingHelper.getItems()
            }
        }
        .task {
            for await loadState in pagingHelper.loadStateFlow {
                switch onEnum(of: loadState.append) {
                case .error(let errorState):
                    self.errorMessage = errorState.error.message
                    break
                case .loading(_):
                    break
                case .notLoading(let notLoadingState):
                    self.hasNextPage = !notLoadingState.endOfPaginationReached
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
}

@ViewBuilder
private func getLoadindView() -> some View {
    HStack {
        ListLoadingItem()
    }
    .listRowInsets(EdgeInsets())
    .listRowSeparator(.hidden, edges: .all)
    .listRowBackground(Color.clear)
}

@ViewBuilder
private func getErrorView(
    errorMessage: String,
    onRetryClicked: @escaping () -> Void
) -> some View {
    HStack {
        ListErrorItem(
            errorMessage: errorMessage,
            onRetryClicked: onRetryClicked
        )
    }
    .listRowInsets(EdgeInsets())
    .listRowSeparator(.hidden, edges: .all)
    .listRowBackground(Color.clear)
}

@ViewBuilder
private func getAlbumDetailItemView(
    albumItem: AlbumItem,
    albumDetailItem: AlbumDetailItem
) -> some View {
    HStack(spacing: 6) {
        KFImage(URL(string: albumItem.image))
            .resizable()
            .placeholder({
                Image("Placeholder")
                    .resizable()
                    .frame(width: 50, height: 50)
                    .scaledToFit()
            })
            .startLoadingBeforeViewAppear()
            .frame(width: 50, height: 50)
            .cornerRadius(4)
            .scaledToFit()
        
        VStack(alignment: .leading) {
            Text(albumDetailItem.trackName)
                .foregroundColor(Color.white)
                .font(.system(size: 14, weight: .semibold))
            
            Text(albumDetailItem.artists)
                .foregroundColor(Color.white)
                .font(.system(size: 14, weight: .regular))
                .padding(.top, 2)
            
        }
    }
    .padding(.vertical, 12)
    .listRowInsets(EdgeInsets())
    .listRowBackground(Color.clear)
}
