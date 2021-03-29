//
//  Sell.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 22.03.2021.
//

import Foundation
import Alamofire


class Sell : Codable , Identifiable  {
  

    public var id:String?
    public var refURL:String?
    public var istPrivateLike:String?
    public var istPrivateDislike:String?
    public var category:String?
    public var image:String?
    public var istLike:String?
    public var istView:String?
    public var name:String?
    public var price:String?
    public var priceOld:String?
    public var discountRate:String?
    public var welcomeDescription:String?
    public var buyDescription:String?
    public var optSpecial:String?
    public var optlowPrice:String?
    public var optNew:String?
    public var optKing:String?
    public var optGift:String?
    public var optSuper:String?
    public var fDate:String?
    public var cDate:String?
    public var userLike:String?
    public var status:String?
 

    
}


class SellData : ObservableObject {
    @Published  var sells = [Sell]()
    
    func load () {
        let ID = UserDefaults.standard.object(forKey: "ID")
        print((ID!))
        print("dadasda")
        let parametreler:Parameters = ["page":"product","guid":ID!,"list":""]
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
           
            
            if let data  = response.data {
                
                do {
                    
               
                    self.sells = try JSONDecoder().decode([Sell].self, from: data)

                    print(self.sells)
                   // if let kisiListesi = cevap.bilgiler {
                      
                        
                   // }
                }catch{
                    print(error.localizedDescription)
                }
                
            }
            
        }
    }
}

