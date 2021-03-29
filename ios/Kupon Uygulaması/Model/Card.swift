//
//  Card.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 25.03.2021.
//


import UIKit
import Foundation
import Alamofire

class CardItem : Identifiable  {
    var x:CGFloat = 0.0
    var y:CGFloat = 0.0
    var degree:Double = 0.0
    
}



class Card :  Codable , Identifiable  {
    

    
    var id:String
    var refUrl:String
    var category:String
    var image:String
    var istLike:String
    var istView:String
    var name:String
    var price:String
    var priceOld:String
    var discountRate:String
    var description:String
    var buyDescription:String
    var optSpecial:String
    var optlowPrice:String
    var optNew:String
    var optKing:String
    var optGift:String
    var optSuper:String
    var fDate:String
    var cDate:String
    var status:String
    var userLike:String
    

    init(id:String,refUrl:String,category:String,image:String,istLike:String,istView:String,name:String,price:String,priceOld:String,discountRate:String,description:String
         ,buyDescription:String,optSpecial:String,optlowPrice:String,optNew:String,optKing:String,optGift:String,optSuper:String,fDate:String,cDate:String,status:String,userLike:String) {
        
        self.id = id
        self.refUrl = refUrl
        self.category = category
        self.image = image
        self.istLike = istLike
        self.istView = istView
        self.name = name
        self.price = price
        self.priceOld = priceOld
        self.discountRate = discountRate
        self.description = description
        self.buyDescription = buyDescription
        self.optSpecial = optSpecial
        self.optlowPrice = optlowPrice
        self.optNew = optNew
        self.optKing = optKing
        self.optGift = optGift
        self.optSuper = optSuper
        self.fDate = fDate
        self.cDate = cDate
        self.status = status
        self.userLike = userLike
        
    }
   
    
}

class CardData : ObservableObject {
    @Published  var cards = [Card]()
 
    func load () {
        let ID = UserDefaults.standard.object(forKey: "ID")
        print("dadasda")
        let parametreler:Parameters = ["page":"product","guid":ID!,"list":""]
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
           
            
            if let data  = response.data {
                
                do {
                    
               
                    let cardss = try JSONDecoder().decode([Card].self, from: data)

                    print(self.cards)
                   // if let kisiListesi = cevap.bilgiler {
           
                    DispatchQueue.main.async {
                          self.cards = cardss
                        
                       }
                
                    print("burada load tatattatata")
                   
                  
                    
                        
                   // }
                }catch{
                    print(error.localizedDescription)
                }
                
            }
            
        }
    }
}
