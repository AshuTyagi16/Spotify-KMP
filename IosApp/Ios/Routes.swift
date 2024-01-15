//
//  Routes.swift
//  Ios
//
//  Created by Ashu Tyagi on 15/01/24.
//

import Foundation
import SwiftUI

enum AppRoute: Equatable {
    static func == (lhs: AppRoute, rhs: AppRoute) -> Bool {
        return lhs.key == rhs.key
    }
    
    case PlaylistDetail(
        playlistId: String
    )
    
    case AlbumDetail(
        albumId: String
    )
    
    var key: String {
        switch self {
        case .PlaylistDetail:
            return "PlaylistDetail"
        case .AlbumDetail:
            return "AlbumDetail"
        }
    }
}

extension AppRoute {
    @ViewBuilder
    func getView() -> some View {
        switch self {
        case.PlaylistDetail(playlistId: let playlistId):
            PlaylistDetailScreen(playlisId: playlistId)
        case .AlbumDetail(albumId: let albumId):
            EmptyView()
        }
    }
}
