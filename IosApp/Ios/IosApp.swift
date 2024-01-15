//
//  IosApp.swift
//  Ios
//
//  Created by Ashu Tyagi on 13/01/24.
//

import SwiftUI
import FlowStacks
import shared

@main
struct IosApp: App {
    
    @State private var routes: Routes<AppRoute> = []
    
    init() {
        KoinInitializerKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            HomePageScreen()
                .showing($routes, embedInNavigationView: true) { route, _ in
                    route.getView()
                        .hideNavigationBar()
                }
        }
    }
}
