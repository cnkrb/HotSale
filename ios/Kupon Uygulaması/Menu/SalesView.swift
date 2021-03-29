//
//  SalesView.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 18.03.2021.
//

import SwiftUI
import Alamofire
import Foundation

struct SalesItem: View {
    
    
    
    @ObservedObject var sales = SalesData()
    
    var item : Sales
    @Binding var showImagePicker: Bool
    @Binding var image: Image?
    @Binding var showShareSheet : Bool

    var body: some View {
        
        ZStack{
        VStack (spacing : 6){
            
          
            
            Button(action: {
                self.showImagePicker=true
                if item.status == "0"  {
                }else {
                }
            }, label: {
                VStack (spacing:10){
                    
                        ZStack {
                            
                            
                            let fullName    = item.image!
                            let fullNameArr = fullName.components(separatedBy: ",")

                        
                            if let url = URL(string: fullNameArr[0]) {
                              
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
                     
               
                   
                    
                    Text(item.name!).accentColor(.black).lineLimit(2).font(.system(size: 14))
                    
                  

                }
                
            })
            
            if item.status == "1" {
                Text("Onaylandı").foregroundColor(Color.red)
            }else {
                Button(action: {
                    self.showImagePicker=true
                    self.showShareSheet=true
                
                    
              
                    
                }, label: {
                    Text("Tıkla ve Onayla").foregroundColor(Color.red)
                })
            }
            
           
        
        } .padding(5)
        .background(Color.white)
        .cornerRadius(15)
        
        
      
      
    }
        
        
    }
    

    
 
    

    
}

struct SalesView: View {
    @ObservedObject var sales = SalesData()
    @Binding var editText: String
 
    @Binding var showImagePicker: Bool
    @Binding var image: Image?
    @Binding var showShareSheet : Bool
    var body: some View {
        VStack {
            ZStack{
                
            Color("Gray")
           
            ScrollView {
            LazyVGrid(columns: gridLayout, content: {
             
                ForEach(self.sales.sales.filter({"\(String(describing: $0.name))".lowercased().contains(self.editText.lowercased()) || self.editText.isEmpty
                    
                }))  { saless in
                 
                    SalesItem(sales: self.sales, item: saless,showImagePicker: $showImagePicker,image:$image,showShareSheet: $showShareSheet)
                        .previewLayout(.fixed(width: 200, height: 300))
                        .padding()
                        }
                
                 
            }) //: GRID
           
            }}
          
        }
   
        .onAppear() {
            self.sales.load()
            
            
                
        }
      
    }
}

struct SalesView_Previews: PreviewProvider {
    static var previews: some View {
        SalesView(sales: SalesData(),editText: Binding.constant("0"),showImagePicker: Binding.constant(false),image: .constant(nil),showShareSheet: Binding.constant(false))
    }
}

