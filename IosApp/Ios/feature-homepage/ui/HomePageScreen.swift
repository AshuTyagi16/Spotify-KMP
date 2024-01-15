//
//  HomePageScreen.swift
//  Ios
//
//  Created by Ashu Tyagi on 14/01/24.
//

import Foundation
import SwiftUI
import Kingfisher
import FlowStacks
import shared

struct HomePageScreen : View {
    
    @EnvironmentObject private var navigator: FlowNavigator<AppRoute>
    
    @State
    private var viewModel: HomePageViewModel?
    
    @State
    private var uiState: HomePageContractHomePageState = HomePageContractHomePageStateIdle()
    
    
    var body: some View {
        VStack {
            getViewForState(
                uiState: uiState,
                onPlaylistClick: { playlist in
                    navigator.push(
                        .PlaylistDetail(
                            playlistItem: playlist
                        )
                    )
                },
                onAlbumClick: { albumItem in
                    navigator.push(
                        .AlbumDetail(albumItem: albumItem)
                    )
                }
            )
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
                        try? await skie(viewModel).setEvent(event: HomePageContractEventOnFetchHomePageEvent())
                    }
                    for await uiState in viewModel.uiState {
                        self.uiState = uiState.state
                    }
                }, onCancel: {
                    viewModel.clear()
                    DispatchQueue.main.async {
                        self.viewModel = nil
                    }
                }
            )
        }
    }
    
}

@ViewBuilder
private func getViewForState(
    uiState: HomePageContractHomePageState,
    onPlaylistClick: @escaping (_ playlistItem: PlaylistItem) -> Void,
    onAlbumClick: @escaping (_ albumItem: AlbumItem) -> Void
) -> some View {
    switch onEnum(of: uiState) {
    case .idle(_):
        EmptyView()
    case.loading(_):
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
                HStack {
                    ForEach(state.playlists, id: \.id) { playlist in
                        VStack(alignment: .leading) {
                            KFImage(URL(string: playlist.image))
                                .resizable()
                                .placeholder({
                                    Image("Placeholder")
                                        .resizable()
                                        .frame(width: 200, height: 200)
                                        .scaledToFit()
                                })
                                .startLoadingBeforeViewAppear()
                                .scaledToFit()
                                .frame(width: 200, height: 200)
                                .cornerRadius(12)
                                .onTapGesture {
                                    onPlaylistClick(playlist)
                                }
                            
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
                                .resizable()
                                .placeholder({
                                    Image("Placeholder")
                                        .resizable()
                                        .frame(width: 200, height: 200)
                                        .scaledToFit()
                                })
                                .startLoadingBeforeViewAppear()
                                .scaledToFit()
                                .frame(width: 200, height: 200)
                                .cornerRadius(12)
                                .onTapGesture {
                                    onAlbumClick(album)
                                }
                            
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
