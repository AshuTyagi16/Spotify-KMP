//
//  HomeScreenLoadingPlaceholder.swift
//  Ios
//
//  Created by Ashu Tyagi on 14/01/24.
//

import Foundation
import SwiftUI
import Shimmer

struct HomeScreenLoadingPlaceholder : View {
    
    var body: some View {
        VStack {
            Spacer()
                .frame(height: 200)
                .background(Color.black)
            ForEach((1...4), id: \.self) { index in
                ScrollView(.horizontal) {
                    HStack {
                        ForEach((1...4), id: \.self) { index in
                            VStack(alignment: .leading) {
                                ZStack {
                                    
                                }
                                .frame(width: 200, height: 200)
                                .background(RoundedRectangle(cornerRadius: 12).fill(Color.white.opacity(0.2)))
                                
                                Text("")
                                    .frame(width: 180, height: 8)
                                    .background(Color.white.opacity(0.2))
                                    .padding(.horizontal, 6)
                            }
                        }
                    }
                }
                .padding(.vertical, 10)
                .padding(.horizontal, 6)
            }
            Spacer()
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
        .shimmering()
        .background(Color.black)
    }
    
}

#Preview {
    HomeScreenLoadingPlaceholder()
}
