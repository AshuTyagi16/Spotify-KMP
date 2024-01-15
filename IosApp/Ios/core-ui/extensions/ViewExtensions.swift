//
//  ViewExtensions.swift
//  Ios
//
//  Created by Ashu Tyagi on 15/01/24.
//

import Foundation
import SwiftUI

extension View {
    @ViewBuilder
    func hideNavigationBar() -> some View{
        if #available(iOS 16.0, *) {
            self
                .navigationBarBackButtonHidden(true)
        } else {
            self
                .navigationBarTitle("")
                .navigationBarBackButtonHidden(true)
                .navigationBarHidden(true)
        }
        
    }
}
