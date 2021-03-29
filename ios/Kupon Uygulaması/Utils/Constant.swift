//
//  Constant.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 20.03.2021.
//

import SwiftUI



let columnSpacing: CGFloat = 5
let rowSpacing: CGFloat = 5
var gridLayout: [GridItem] {
    return Array(repeating: GridItem( spacing: rowSpacing), count: 2)
}


var gridLayoutThree: [GridItem] {
    return Array(repeating: GridItem( spacing: rowSpacing), count: 3)
}


var gridLayoutOne: [GridItem] {
    return Array(repeating: GridItem( spacing: rowSpacing), count: 1)
}
