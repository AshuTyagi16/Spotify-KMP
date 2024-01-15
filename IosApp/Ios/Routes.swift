//
//  Routes.swift
//  Ios
//
//  Created by Ashu Tyagi on 15/01/24.
//

import Foundation
import SwiftUI
import shared

enum AppRoute: Equatable {
    static func == (lhs: AppRoute, rhs: AppRoute) -> Bool {
        return lhs.key == rhs.key
    }
    
    case PlaylistDetail(
        playlistItem: PlaylistItem
    )
    
    case AlbumDetail(
        albumItem: AlbumItem
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
        case.PlaylistDetail(playlistItem: let playlistItem):
            PlaylistDetailScreen(playlistItem: playlistItem)
        case .AlbumDetail(albumItem: let albumItem):
            AlbumDetailScreen(albumItem: albumItem)
        }
    }
}
