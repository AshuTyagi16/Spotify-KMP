//
//  ListLoadingItem.swift
//  Ios
//
//  Created by Ashu Tyagi on 15/01/24.
//

import Foundation
import SwiftUI
import ProgressIndicatorView

struct ListLoadingItem: View {
    
    @State private var progressForDefaultSector: CGFloat = 0.0
    
    private let timer = Timer.publish(every: 1 / 15, on: .main, in: .common).autoconnect()
    
    var body: some View {
        VStack(alignment: .center, spacing: 12) {
            Text("Loading...")
                .foregroundColor(.white)
            
            ProgressIndicatorView(
                isVisible: .constant(true), type: .default(progress: $progressForDefaultSector)
            )
            .frame(width: 20.0, height: 20.0)
            .foregroundColor(.white)
            .padding(.top, 4)
            
        }
        .frame(maxWidth: .infinity, maxHeight: 80, alignment: .center)
        .onReceive(timer) { _ in
            if progressForDefaultSector >= 1.5 {
                progressForDefaultSector = 0
            } else {
                progressForDefaultSector += 1 / 10
            }
        }
        .onDisappear {
            timer.upstream.connect().cancel()
        }
    }
}

#Preview {
    ListLoadingItem()
        .background(Color.black)
}
