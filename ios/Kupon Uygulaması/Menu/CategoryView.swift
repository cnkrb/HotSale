//
//  CategoryView.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 18.03.2021.
//

import SwiftUI
import Alamofire


struct CategoryItem: View {
    @ObservedObject var category = CategoryData()
    var item : Category
    var body: some View {
        
        VStack (spacing : 6){
            
            Button(action: {
                if item.selected == "0"  {
                    ekle(value: item.id!)
                }else {
                    `sil`(value: item.id!)
                }
            }, label: {
                VStack (spacing:10){
                    
                    if item.selected == "0" {
                        
                        ZStack {
                            
                            if let url = URL(string: item.image!) {
                              
                                    AsyncImage(
                                        url: url
                                        ,placeholder: { Text("Loading ...") },
                                       image: { Image(uiImage: $0).resizable()
                                        
                                         }
                                    ).scaledToFit().padding(10)
                                
                            } else {
                                // the URL was bad!
                            }
                        
                        } //: ZSTACK
                        .background(Color("Renk2"))
                        .cornerRadius(12)
                        
                    }else {
                        ZStack {
                            
                            if let url = URL(string: item.image!) {
                              
                                    AsyncImage(
                                        url: url
                                        ,placeholder: { Text("Loading ...") },
                                       image: { Image(uiImage: $0).resizable()
                                        
                                         }
                                    ).scaledToFit().padding(10)
                                
                            } else {
                                // the URL was bad!
                            }
                        
                        } //: ZSTACK
                        .background(Color("Renk2"))
                        .cornerRadius(12)
                        
                        .overlay(RoundedRectangle(cornerRadius: 10)
                                    .stroke(Color.black, lineWidth: 2))
                        .shadow(radius: 10)
                    }
                   
                    
                    Text(item.name!).accentColor(.black).lineLimit(2).font(.system(size: 14))

                }
                
            })
            
        
        }
    }
    func ekle (value : String) {
        let ID = UserDefaults.standard.object(forKey: "ID")
        print(ID!)
            let parametreler:Parameters = ["page":"category","guid":ID!,"category":value]

       

        
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
           
            
            if let data  = response.data {
                
                do {
                    
                    let cevap = try JSONDecoder().decode(Response.self, from: data)
                    
                   // if let kisiListesi = cevap.bilgiler {
                        
                            
                    print("Kisi id  : \(cevap.message!)")
                    if cevap.result! {
                        print("buradaaaaaaa")
                        self.category.load()
                    }
                    
                        
                    
                        
                   // }
                }catch{
                    print(error.localizedDescription)
                }
                
            }
            
             
        }
    }
    
    func sil (value : String) {
        let ID = UserDefaults.standard.object(forKey: "ID")
        print(ID!)

    
            let parametreler:Parameters = ["page":"category","guid":ID!,"ID":value]

        
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
           
            
            if let data  = response.data {
                
                do {
                    
                    let cevap = try JSONDecoder().decode(Response.self, from: data)
                    
                   // if let kisiListesi = cevap.bilgiler {
                        
                            
                    print("Kisi id  : \(cevap.message!)")
                    if cevap.result! {
                        print("buradaaaaaaa")
                        self.category.load()
                    }
                    
                        
                    
                        
                   // }
                }catch{
                    print(error.localizedDescription)
                }
                
            }
            
             
        }
    }
}

struct CategoryView: View {
    @ObservedObject var category = CategoryData()
    @Binding var editText: String

    var body: some View {
        VStack {
            ScrollView {
            LazyVGrid(columns: gridLayoutThree, content: {
             
                ForEach(self.category.categorys.filter({"\(String(describing: $0.name))".lowercased().contains(self.editText.lowercased()) || self.editText.isEmpty
                    
                }))  { categoryy in
                 
                    CategoryItem(category: self.category, item: categoryy)
                        .previewLayout(.fixed(width: 200, height: 300))
                        .padding()
                        }
                
                 
            }) //: GRID
           
            }
          
        }
   
        .onAppear() {
            self.category.load()
        }
    }
}

struct CategoryView_Previews: PreviewProvider {
    static var previews: some View {
        CategoryView(category: CategoryData(),editText: Binding.constant("0"))
    }
}
