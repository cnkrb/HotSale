//
//  ProductView.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 18.03.2021.
//

import SwiftUI
import UIKit
import Alamofire
import Foundation


struct Slider: View {
    @State var images : String
    @State private var selection = 0
    ///  images with these names are placed  in my assets
    
    func previous () {
        withAnimation {
            selection = selection > 0 ? selection - 1 : images.count-1
        }
    }
    
    func next () {
        withAnimation {
            selection = selection < images.count ? selection + 1 : 0
        }
    }
    
    var body: some View {
        GeometryReader { geo in
        ZStack{
            
            
            TabView(selection : $selection){
                
                
           
              
              //  let lineItems = images.split(separator: ",")
             
            
                if let url = URL(string:"http://market.cenkkaraboa.com/public/images/altkatagori/5fdbffe151fc3.png") {//
                    
                        AsyncImage(
                            url:url
                                              ,placeholder: { Text("") },
                                              image: { Image(uiImage: $0).resizable()
                                              
                                              }
                                          )
                        
                   }
                    
               
               
               
             
                
            }.tabViewStyle(PageTabViewStyle())
            .clipShape(RoundedRectangle(cornerRadius: 5))
            .padding()
            .frame(width:geo.size.width,height:geo.size.height/3)
            
            HStack{
               
                
                Button(action: {
                   previous()
                }, label: {
                    Text("").frame(width:200,height:300)

                }).frame(width:geo.size.width/2,height:geo.size.height/3)
                
                Button(action: {
                    next()
                }, label: {
                    Text("").frame(width:200,height:300)
                }).frame(width:geo.size.width/2,height:geo.size.height/3)
            }.frame(width:geo.size.width,height:geo.size.height/3)

        }
        }
        
        
    }
    
}

struct CardView: View {
    @ObservedObject var cardd = CardData()
    @State var card : Card
    @State var x:CGFloat = 0.0
    @State var y:CGFloat = 0.0
    @State var degree:Double = 0.0
    @Environment(\.openURL) var openURL
    @Binding var  index : Int

    @State var chnage : Bool = false
    @Binding var url : String
    
    @Binding var sepet : Bool
    
    @Binding var showShareSheet : Bool
    @Binding var note : String
    @Binding var showSharProduct : Bool

    let cardGradient = Gradient(colors: [Color.black.opacity(0),Color.black.opacity(0.5)])
    var body: some View {
        
        GeometryReader { geo in
      
        VStack {
        
        ZStack{
            Color.white
        VStack{
   
            VStack{
                
                //Slider(images:card.image)
                if let url = URL(string:card.image) {//
                    
                        AsyncImage(
                            url:url
                                              ,placeholder: { Text("") },
                                              image: { Image(uiImage: $0).resizable()
                                              
                                              }
                                          )
                        
                   }
              
            }
            
            
            ZStack{
      
                
                VStack{
                    HStack (alignment:.center){
                        Button(action: {
                            self.chnage=true
                        }, label: {
                            Text("AÇIKLAMA")       .fontWeight(.bold).font(.system(size: 15)).foregroundColor(self.chnage ? .red : .black)
                }).padding(.top,10)
                Spacer ()
                Button(action: {
                    self.chnage=false
                    
                    print(card.image)
                    let da = card.image.split(separator: ",")
                    print(da)
                }, label: {
                    Text("NASIL ALABİLİRİM")
                        .fontWeight(.bold)  .font(.system(size: 15)).foregroundColor(self.chnage ? .black : .red).frame(alignment: /*@START_MENU_TOKEN@*/.center/*@END_MENU_TOKEN@*/).multilineTextAlignment(.center)
                })
                
                Spacer ()
                        Button(action: {
                            
                        }, label: {
                    Text("KUPONLAR")         .fontWeight(.bold).font(.system(size: 15)).foregroundColor(.black)
                   
                }).padding(.top,10)
            }.padding(.leading,10)
            .padding(.trailing,10)
                    
            Divider()
            
            HStack{
                Text(card.priceOld).padding(.top,10).foregroundColor(.red)
               
                Spacer ()
                Text("%"+card.discountRate+" İNDİRİM").foregroundColor(.red)
                                
                Spacer ()
                    Text(card.price).padding(.top,10).foregroundColor(.orange)
                
            }.padding(.leading,15)
            .padding(.trailing,15)
            
            
                    Text(self.chnage ? card.description : card.buyDescription).font(.system(size: 14)).padding(10)
                
            }
                
               
            }
            
            
        }
            ZStack {
                Image("hand.up.fill")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: UIScreen.main.bounds.width/4)
                    .foregroundColor(Color.green)
                    .opacity(Double(x/10 - 1))

                Image("hand.down.fill")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: UIScreen.main.bounds.width/4)
                    .foregroundColor(Color.red)
                    .opacity(Double(x / 10 * -1  - 1))
            }.opacity(0.4)
    }
        .cornerRadius(10)
        .shadow(radius: 10)
        .offset(x:self.x,y: self.y)
        .rotationEffect(.init(degrees: self.degree))
        
        .gesture(
            DragGesture()
                .onChanged { value in
                    
                    withAnimation(.default){
                    
                        self.x=value.translation.width
                        self.y=value.translation.height
                        self.degree=7*(value.translation.width > 0 ? 1 : -1)
                    }
                }
                .onEnded { value in
                    withAnimation(.interpolatingSpring(mass: 1.0, stiffness: 50, damping: 8, initialVelocity: 0)) {
                        
                        switch value.translation.width {

                        case 0...100:
                            self.x = 0; self.degree = 0; self.y = 0
                                
                            print("1")
                            print(card.refUrl)
                        case let x where x > 100:
                            self.url=card.refUrl
                            print(self.index)
                            self.index = self.index + 1
                            print(self.index)
                            print("2")
                            self.x = 500;  self.degree = 12
                            print(card.refUrl)
                        case (-100)...(-1):
                            self.x = 0; self.degree = 0; y = 0
                            print("3")
                            print(card.refUrl)
                        case let x where x < -100:
                            self.x = -500; self.degree = -12
                            self.url=card.refUrl
                            print("4")
                            print(self.index)
                            self.index = self.index + 1
                            print(self.index)
                            print(card.refUrl)
                        default: self.x = 0 ; self.y = 0
                        }
                        
                    }
                }
                   
        
        
        
        )
    
      
            HStack{
                Button(action: {
                    print(self.cardd.cards[self.index].userLike)
                    if self.cardd.cards[self.index].userLike == "1" {
                        favcikar()
                    }else {
                        favEkle()
                    }
                 
                    
                }) {
                    Image( self.cardd.cards[self.index].userLike == "1" ?  "heart.fill" : "heart").foregroundColor(Color("Renk4"))
                        .padding(10)
                       

                }
                .frame(width:geo.size.width/5)
                .cornerRadius(15)
                .overlay(RoundedRectangle(cornerRadius: 15)
                            .stroke(Color("Renk4"), lineWidth: 2))
                .shadow(radius: 15)
                .padding(5)
                
                
                
                Button(action: {
                    print("index: " + String(self.index))
                    ekle()
                   openURL(URL(string: self.cardd.cards[self.index].refUrl)!)
                    

                }) {
                    Text("Sepete Ekle")
                        .fontWeight(.bold)
                        .font(.headline)
                        .padding(10)
                        .frame(width:geo.size.width/5*2.4)
                        .background(Color("Renk4"))
                        .foregroundColor(.white)
                        .cornerRadius(15)

                }
                
                
                Button(action: {
                    self.note=self.cardd.cards[self.index].refUrl

                    self.showSharProduct=true
                   self.showShareSheet=true
                    
                    print(self.note)
           
                }) {
                    Image("share").foregroundColor(Color("Renk4")).padding(10)
                   
                }
                .frame(width:geo.size.width/5)
                .cornerRadius(15)
                .overlay(RoundedRectangle(cornerRadius: 15)
                            .stroke(Color("Renk4"), lineWidth: 2))
                .shadow(radius: 15)
                .padding(5)
            }
        
    }
    
       
            
        }
        
    }
    
    func ekle() {
        
        //    Call<DefaultResponse> searchSave(@Field("page") String page, @Field("guid") String guid, @Field("search") String search);

        let ID = UserDefaults.standard.object(forKey: "ID")
        print(ID!)
        let parametreler:Parameters = ["page":"sales","guid":ID!,"productid":self.cardd.cards[self.index].id]
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
           
            
            if let data  = response.data {
                
                do {
                    
                    let cevap = try JSONDecoder().decode(Response.self, from: data)
                    
                   // if let kisiListesi = cevap.bilgiler {
                        
                            
                    print("Kisi id  : \(cevap.message!)")
                    if cevap.result! {
                        print("buradaaaaaaa")
                       
                        
                    }
                    
                        
                    
                        
                   // }
                }catch{
                    print(error.localizedDescription)
                }
                
            }
            
             
        }
    }
    
    func favEkle() {
        
        //    Call<DefaultResponse> searchSave(@Field("page") String page, @Field("guid") String guid, @Field("search") String search);

        let ID = UserDefaults.standard.object(forKey: "ID")
        print(ID!)
        let parametreler:Parameters = ["page":"like","guid":ID!,"productid":self.cardd.cards[self.index].id]
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
           
            
            if let data  = response.data {
                
                do {
                    
                    let cevap = try JSONDecoder().decode(Response.self, from: data)
                    
                   // if let kisiListesi = cevap.bilgiler {
                        
                            
                    print("Kisi id  : \(cevap.message!)")
                    if cevap.result! {
                        print("buradaaaaaaa")
                        self.cardd.load()
                        
                    }
                    
                        
                    
                        
                   // }
                }catch{
                    print(error.localizedDescription)
                }
                
            }
            
             
        }
    }
    
    func favcikar() {
        
        //    Call<DefaultResponse> searchSave(@Field("page") String page, @Field("guid") String guid, @Field("search") String search);

        let ID = UserDefaults.standard.object(forKey: "ID")
        print(ID!)
        let parametreler:Parameters = ["page":"like","guid":ID!,"ID":self.cardd.cards[self.index].id]
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
           
            
            if let data  = response.data {
                
                do {
                    
                    let cevap = try JSONDecoder().decode(Response.self, from: data)
                    
                   // if let kisiListesi = cevap.bilgiler {
                        
                            
                    print("Kisi id  : \(cevap.message!)")
                    if cevap.result! {
                        print("buradaaaaaaa")
                        self.cardd.load()

                        
                    }
                    
                        
                    
                        
                   // }
                }catch{
                    print(error.localizedDescription)
                }
                
            }
            
             
        }
    }
    
}

struct ProductView: View {
    @ObservedObject var card = CardData()
    @Environment(\.openURL) var openURL
    @Binding var showShareSheet : Bool
    @Binding var note : String
    @Binding var showSharProduct : Bool

    @State var url : String = ""
    @State var sepet : Bool = false
    @State var index : Int = 0
    
    var body: some View {
        
      
        
        GeometryReader{ geo in
            
            
            
            ZStack{
                
            Color("Gray")
            VStack{
                

                
                
                ZStack {
                    
                    
                    
                    ForEach (self.card.cards.reversed() ) { cardd in
                        
                        CardView(cardd:self.card,card: cardd, index: $index, url:$url, sepet: $sepet,showShareSheet:$showShareSheet,note:$note, showSharProduct: $showSharProduct).padding()
                       
                    }
                    
                    
                }.zIndex(1.0)
                 
         
            }
            }
        }.onAppear() {
            print("asdadsad")
            self.card.load()
            
    
               
            
        }
        
    }
    
    
}

struct ProductView_Previews: PreviewProvider {
    static var previews: some View {
        ProductView(card:CardData(),showShareSheet: Binding.constant(false),note:Binding.constant(""), showSharProduct: Binding.constant(false))
    }
}
