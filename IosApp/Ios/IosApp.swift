//
//  IosApp.swift
//  Ios
//
//  Created by Ashu Tyagi on 13/01/24.
//

import SwiftUI
import shared

@main
struct IosApp: App {
    
    init() {
        KoinInitializerKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
