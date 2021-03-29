//
//  ShapeView.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 18.03.2021.
//

import SwiftUI

struct ShapeView: Shape {
    var yOffset: CGFloat = 50
    
    func path(in rect: CGRect) -> Path {
      var path = Path()
        
        path.move(to: CGPoint.zero)
        path.addLine(to: CGPoint(x: rect.maxX, y:0))
        path.addLine(to: CGPoint(x: rect.maxX, y:rect.maxY - yOffset))
        path.addQuadCurve(to: CGPoint(x: 0, y: rect.maxY - yOffset), control: CGPoint(x: rect.midX, y: rect.maxY+yOffset))
        
     
        path.closeSubpath()
       
        return path
    }
}

struct ShapeView_Previews: PreviewProvider {
    static var previews: some View {
        ShapeView().frame(height:400)
    }
}
