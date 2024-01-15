//
//  ListErrorItem.swift
//  Ios
//
//  Created by Ashu Tyagi on 15/01/24.
//

import Foundation
import SwiftUI

struct ListErrorItem: View {
    
    var errorMessage: String
    var onRetryClicked : () -> Void = {}
    
    var body: some View {
        VStack(spacing: 0) {
            
            Text(errorMessage)
                .foregroundColor(Color.white)
                .font(.system(size: 14, weight: .semibold))
                .padding(.vertical, 10)
            
            Button(action: {
                onRetryClicked()
            }) {
                Text("Retry")
                    .foregroundColor(Color.black)
                    .font(.system(size: 14, weight: .semibold))
                    .padding(.all, 8)
            }
            .background(Color.white)
            .cornerRadius(12)
        }
        .padding(.all, 12)
        .frame(maxWidth: .infinity)
    }
}

#Preview {
    ListErrorItem(
        errorMessage: "Som error occurred",
        onRetryClicked: {}
    )
    .background(Color.black)
}
