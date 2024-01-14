//
//  ContentView.swift
//  Ios
//
//  Created by Ashu Tyagi on 13/01/24.
//

import SwiftUI
import shared

struct ContentView: View {
    
    @State
    private var viewModel: HomePageViewModel?
    
    @State
    private var uiState: HomePageContractHomePageState = HomePageContractHomePageStateIdle()
    
    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundStyle(.tint)
            getViewForState(uiState: uiState)
        }
        .padding()
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
    case .idle(_):
        Text("IDLE State")
    case .loading(_):
        Text("LOADING State")
    case .success(let successState):
        Text("SUCCESS State : PLAYLISTS \(successState.playlists.count) : ALBUMS : \(successState.albums.count)")
    }
}

#Preview {
    ContentView()
}
