//
//  PlaylistDetailLoadingPlaceholder.swift
//  Ios
//
//  Created by Ashu Tyagi on 16/01/24.
//

import Foundation
import Shimmer
import SwiftUI

struct DetailLoadingPlaceholder: View {
    var body: some View {
        VStack {
            ForEach((1...10), id: \.self) { _ in
                HStack(alignment: .center, spacing: 6) {
                    RoundedRectangle(cornerRadius: 4)
                        .fill(Color.white.opacity(0.2))
                        .frame(width: 50, height: 50)
                    
                    VStack(alignment: .leading) {
                        Text("")
                            .frame(width: 180, height: 8)
                            .background(Color.white.opacity(0.2))
                        
                        Text("")
                            .frame(width: 120, height: 8)
                            .background(Color.white.opacity(0.2))
                    }
                    Spacer()
                }
                .frame(maxWidth: .infinity, maxHeight: 80)
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .shimmering()
    }
}

#Preview {
    DetailLoadingPlaceholder()
}
