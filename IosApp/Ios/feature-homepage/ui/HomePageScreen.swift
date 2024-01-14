//
//  HomePageScreen.swift
//  Ios
//
//  Created by Ashu Tyagi on 14/01/24.
//

import Foundation
import SwiftUI
import Kingfisher
import shared

struct HomePageScreen : View {
    
    @State
    private var viewModel: HomePageViewModel?
    
    @State
    private var uiState: HomePageContractHomePageState = HomePageContractHomePageStateIdle()
    
    var body: some View {
        VStack {
            getViewForState(uiState: uiState)
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
        .background(Color.black)
        .task {
            let viewModel = SharedModuleDependencies.shared.homePageViewModel
            await withTaskCancellationHandler(
                operation: {
                    self.viewModel = viewModel
                    Task {
                        try? await viewModel.doInit()
                    }
                    Task {
                        try? await Task.sleep(nanoseconds: 500)
                        try? await viewModel.setEvent(event: HomePageContractEventOnFetchHomePageEvent())
                    }
                    for await uiState in viewModel.uiState {
                        self.uiState = uiState.state
                    }
                }, onCancel: {
                    viewModel.clear()
                    self.viewModel = nil
                }
            )
        }
    }
    
}

@ViewBuilder
private func getViewForState(uiState: HomePageContractHomePageState) -> some View {
    switch onEnum(of: uiState) {
    case .idle(let state):
        EmptyView()
    case.loading(let state):
        VStack(alignment: .center) {
            HomeScreenLoadingPlaceholder()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
    case .success(let state):
        VStack(spacing: 0) {
            
            Spacer()
                .frame(height: 20)
            
            Text("Featured Playlists")
                .foregroundColor(.white)
                .font(.system(size: 24, weight: .bold))
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.horizontal, 6)
                .padding(.vertical, 10)
            
            ScrollView(.horizontal) {
                LazyHStack {
                    ForEach(state.playlists, id: \.id) { playlist in
                        VStack(alignment: .leading) {
                            KFImage(URL(string: playlist.image))
                                .startLoadingBeforeViewAppear()
                                .scaledToFit()
                                .frame(width: 200, height: 200)
                                .cornerRadius(12)
                            
                            Text(playlist.name)
                                .foregroundColor(.white)
                                .font(.system(size: 12, weight: .bold))
                                .frame(maxWidth: 200, alignment: .leading)
                                .lineLimit(1)
                                .padding(.top, 4)
                                .padding(.bottom, 1)
                            
                            Text(playlist.description_)
                                .foregroundColor(.white)
                                .font(.system(size: 10, weight: .regular))
                                .frame(maxWidth: 200, alignment: .leading)
                                .lineLimit(1)
                        }
                        .padding(.horizontal, 4)
                    }
                }
            }
            .padding(.horizontal, 6)
            .fixedSize(horizontal: false, vertical: /*@START_MENU_TOKEN@*/true/*@END_MENU_TOKEN@*/)
            
            Spacer()
                .frame(height: 20)
            
            Text("Featured Albums")
                .foregroundColor(.white)
                .font(.system(size: 24, weight: .bold))
                .frame(maxWidth: .infinity, alignment: .leading)
                .padding(.horizontal, 6)
                .padding(.vertical, 10)
            
            ScrollView(.horizontal) {
                LazyHStack {
                    ForEach(state.albums, id: \.id) { album in
                        VStack(alignment: .leading) {
                            KFImage(URL(string: album.image))
                                .startLoadingBeforeViewAppear()
                                .scaledToFit()
                                .frame(width: 200, height: 200)
                                .cornerRadius(12)
                            
                            Text(album.name)
                                .foregroundColor(.white)
                                .font(.system(size: 12, weight: .bold))
                                .frame(maxWidth: 200, alignment: .leading)
                                .lineLimit(1)
                                .padding(.top, 4)
                                .padding(.bottom, 1)
                            
                            Text(album.artists)
                                .foregroundColor(.white)
                                .font(.system(size: 10, weight: .regular))
                                .frame(maxWidth: 200, alignment: .leading)
                                .lineLimit(1)
                        }
                        .padding(.horizontal, 4)
                    }
                }
            }
            .padding(.horizontal, 6)
            .fixedSize(horizontal: false, vertical: /*@START_MENU_TOKEN@*/true/*@END_MENU_TOKEN@*/)
            
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .top)
    }
}

#Preview {
    HomePageScreen()
}
