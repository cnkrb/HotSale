//
//  Sales.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 22.03.2021.
//


import Foundation
import Alamofire


class Sales : Codable , Identifiable  {
    public var productid:String?
    public var name:String?
    public var image:String?
    public var saleid:String?
    public var status:String?
 
    init(productid:String,name:String,image:String,saleid:String,status:String) {
        self.productid = productid
        self.name = name
        self.image = image
        self.saleid = saleid
        self.status = status
    }
    
    deinit {
    }
    
}


class SalesData : ObservableObject {
    @Published  var sales = [Sales]()
    
    func load () {
        let ID = UserDefaults.standard.object(forKey: "ID")
        print((ID!))
        print("dadasda")
        let parametreler:Parameters = ["page":"sales","guid":ID!,"list":""]
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
           
            
            if let data  = response.data {
                
                do {
                    
               
                    self.sales = try JSONDecoder().decode([Sales].self, from: data)

                    print(self.sales)
                   // if let kisiListesi = cevap.bilgiler {
                      
                        
                   // }
                }catch{
                    print(error.localizedDescription)
                }
                
            }
            
        }
    }
}

