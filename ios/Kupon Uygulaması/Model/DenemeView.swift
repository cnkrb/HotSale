//
//  DenemeView.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 20.03.2021.
//


import Foundation

class DenemeView : Codable {
   public var a:String?
    public var b:String?
    public var c:String?
    
    init(a:String,b:String,c:String) {
        self.a = a
        self.b = b
        self.c = c
    }
    
}
