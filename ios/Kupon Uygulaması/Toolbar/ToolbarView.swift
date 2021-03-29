//
//  ToolbarView.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 18.03.2021.
//

import SwiftUI

struct ToolbarView: View {
    var body: some View {
        (LinearGradient(gradient: Gradient(colors: [Color("Red"), Color("Orange")]), startPoint: .leading, endPoint: .trailing)).ignoresSafeArea()
    }
}

struct ToolbarView_Previews: PreviewProvider {
    static var previews: some View {
        ToolbarView()
    }
}
