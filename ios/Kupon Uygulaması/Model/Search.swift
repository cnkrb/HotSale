//
//  Search.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 20.03.2021.
//

import Foundation
import Alamofire


class Search : Codable , Identifiable  {
   public var id:String?
    public var userid:String?
    public var name:String?
    public var status:String?
    
    init(id:String,userid:String,name:String,status:String) {
        self.id = id
        self.userid = userid
        self.name = name
        self.status = status
    }
    
}

class SearchData : ObservableObject {
    @Published  var searchs = [Search]()
    
    func load () {
        let ID = UserDefaults.standard.object(forKey: "ID")
        print((ID!))
        print("dadasda")
        let parametreler:Parameters = ["page":"search","guid":ID!,"list":""]
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
           
            
            if let data  = response.data {
                
                do {
                    
               
                    let searchss = try JSONDecoder().decode([Search].self, from: data)

                    print(self.searchs)
                   // if let kisiListesi = cevap.bilgiler {
                      
                    
                    DispatchQueue.main.async {
                          self.searchs = searchss
                        
                       }
                    
                    print("burada load ")
                    for kisi in   self.searchs {
                            
                            print("Kisi id  : \(kisi.name!)")
                        
                        }
                    
                        
                   // }
                }catch{
                    print(error.localizedDescription)
                }
                
            }
            
        }
    }
}
