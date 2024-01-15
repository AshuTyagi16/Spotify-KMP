//
//  ListLoadingItem.swift
//  Ios
//
//  Created by Ashu Tyagi on 15/01/24.
//

import Foundation
import SwiftUI

struct ListLoadingItem: View {
    
    var body: some View {
        VStack(alignment: .center, spacing: 0) {
            Text("Loading...")
                .foregroundColor(.white)
                .padding(.vertical, 6)
            ProgressView()
                .progressViewStyle(CircularProgressViewStyle(tint: Color.white))
                .padding(.all, 10)
            
        }
    }
}
