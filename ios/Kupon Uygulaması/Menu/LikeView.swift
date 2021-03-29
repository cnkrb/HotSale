//
//  LikeView.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 18.03.2021.
//

import SwiftUI
import Alamofire
import Foundation

struct LikeItem: View {
    @ObservedObject var like = LikeData()
    var item : Like
    var body: some View {
        
        
        VStack {
            
           
           
            ZStack (alignment : .topTrailing) {
                
                
              
                
                        let fullName    = item.image!
                        let fullNameArr = fullName.split(separator: ",")
                         
                           
                            if let url = URL(string: String(fullNameArr[0])) {
                              
                                    AsyncImage(
                                        url: url
                                        ,placeholder: { Text("Loading ...") },
                                       image: { Image(uiImage: $0).resizable()
                                        
                                         }
                                    ).scaledToFit().padding(10)
                                
                            }
                        
                            
                VStack{
                 
                    VStack (alignment:.leading){
                        Button(action: {
                            
                            delete(id: item.id!)
                           
                        }, label: {
                            Image("trash").resizable().aspectRatio( contentMode: .fit).frame(width:25, height:25).foregroundColor(Color.red)

                        })
                        

                    }
                    
                }
                          
                           
                        
                        
                        
                        
                        } //: ZSTACK
                  
                        
                    Text(item.name!).accentColor(.black).lineLimit(2).font(.system(size: 14))

                
                
                      
            
        
        }
        .padding(7)
        .background(Color.white)
        .cornerRadius(15)
    }
    
    func delete(id:String) {
        let ID = UserDefaults.standard.object(forKey: "ID")
        print(ID!)

    
            let parametreler:Parameters = ["page":"like","guid":ID!,"ID":id]

        
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
           
            
            if let data  = response.data {
                
                do {
                    
                    let cevap = try JSONDecoder().decode(Response.self, from: data)
                    
                   // if let kisiListesi = cevap.bilgiler {
                        
                            
                    print("Kisi id  : \(cevap.message!)")
                    if cevap.result! {
                        print("buradaaaaaaa")
                        self.like.load()
                    }
                    
                        
                    
                        
                   // }
                }catch{
                    print(error.localizedDescription)
                }
                
            }
            
             
        }
    }
    
    
}


struct LikeView: View {
    
    @ObservedObject var like = LikeData()
    @Binding var editText: String
    
    var body: some View {
        ZStack{
            Color("Gray")
         
        VStack {
            ScrollView {
            LazyVGrid(columns: gridLayout, content: {
             
                ForEach(self.like.likes.filter({"\(String(describing: $0.name))".lowercased().contains(self.editText.lowercased()) || self.editText.isEmpty
                    
                }))  { likess in
                 
                    LikeItem(like: self.like, item: likess)
                        .previewLayout(.fixed(width: 200, height: 300))
                        .padding()
                        }
                
                 
            }) //: GRID
           
            }
          
        }
        }
        .onAppear() {
            self.like.load()
        }
       
            
     
    }
}

struct LikeView_Previews: PreviewProvider {
    static var previews: some View {
        LikeView(like:LikeData(),editText: Binding.constant("0"))
    }
}
