//
//  Category.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 22.03.2021.
//

import Foundation
import Alamofire


class Category : Codable , Identifiable  {
   public var id:String?
    public var name:String?
    public var image:String?
    public var selected:String?
    
    init(id:String,name:String,image:String,selected:String) {
        self.id = id
        self.name = name
        self.image = image
        self.selected = selected
    }
    
}


class CategoryData : ObservableObject {
    @Published  var categorys = [Category]()
    
    func load () {
        let ID = UserDefaults.standard.object(forKey: "ID")
        print((ID!))
        print("dadasda")
        let parametreler:Parameters = ["page":"category","guid":ID!,"list":""]
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
           
            
            if let data  = response.data {
                
                do {
                    
               
                    self.categorys = try JSONDecoder().decode([Category].self, from: data)

                    print(self.categorys)
                   // if let kisiListesi = cevap.bilgiler {
                      
                        
                   // }
                }catch{
                    print(error.localizedDescription)
                }
                
            }
            
        }
    }
}
