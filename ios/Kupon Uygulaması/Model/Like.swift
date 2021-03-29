//
//  Like.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 22.03.2021.
//

import Foundation
import Alamofire


class Like : Codable , Identifiable  {
   public var id:String?
    public var name:String?
    public var image:String?
    public var status:String?
    
    init(id:String,name:String,image:String,status:String) {
        self.id = id
        self.name = name
        self.image = image
        self.status = status
    }
    
}


class LikeData : ObservableObject {
    @Published  var likes = [Like]()
    
    func load () {
        let ID = UserDefaults.standard.object(forKey: "ID")
        print((ID!))
        print("dadasda")
        let parametreler:Parameters = ["page":"like","guid":ID!,"list":""]
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
           
            
            if let data  = response.data {
                
                do {
                    
               
                    self.likes = try JSONDecoder().decode([Like].self, from: data)

                    print(self.likes)
                   // if let kisiListesi = cevap.bilgiler {
                      
                        
                   // }
                }catch{
                    print(error.localizedDescription)
                }
                
            }
            
        }
    }
}

