//
//  SellView.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 22.03.2021.
//

import SwiftUI


struct SellItem: View {
    var item : Sell
    var body: some View {
        
        VStack (spacing: 5) {
            
              
                        
                    if let url = URL(string: item.image!) {
                              
                        AsyncImage(
                                url: url
                                ,placeholder: { Text("Loading ...") },
                                image: { Image(uiImage: $0).resizable()}).scaledToFit()
                                
                    }
                 
            Text(item.name!).accentColor(.black).lineLimit(2).font(.system(size: 14)).padding(5)
                
            Button(action: {
               
            }) {
                Text("Seç")
                    .frame(minWidth: 0, maxWidth: .infinity)
                    .background(Color.black)
                    .foregroundColor(.white)
                    .font(.title)
            }.padding(5)
            
         
            
           

                
                
        } .cornerRadius(15)

        .shadow(radius: 10)
        
    }
    
    
}






struct SellView: View {
    @ObservedObject var sell = SellData()

    @State var user = ""
    @State var mail = ""
    @State var number = ""
    @State var address = ""
    @State var productId = ""
    
    var body: some View {
        
     
        VStack {
            ScrollView {
            VStack(alignment: .leading){
                
                Text("Ad Soyad").font(.headline).fontWeight(.light).foregroundColor(Color.init(.label).opacity(0.75))
                
                HStack{
                    
                    TextField("", text: $user)
                        .textFieldStyle(RoundedBorderTextFieldStyle())
                    
                    if user != ""{
                        
                        Image("check").foregroundColor(Color.init(.label))
                    }
                    
                }.padding(.trailing)
                                
            }.padding(.trailing).padding(.leading)
            
            
            VStack(alignment: .leading){
                
                Text("Email").font(.headline).fontWeight(.light).foregroundColor(Color.init(.label).opacity(0.75))
                
                HStack{
                    
                    TextField("", text: $mail)
                        .textFieldStyle(RoundedBorderTextFieldStyle())
                    
                    if user != ""{
                        
                        Image("check").foregroundColor(Color.init(.label))
                    }
                    
                }.padding(.trailing)
                                
            }.padding(.trailing).padding(.leading)
            
            
            VStack(alignment: .leading){
                
                Text("Telefon").font(.headline).fontWeight(.light).foregroundColor(Color.init(.label).opacity(0.75))
                
                HStack{
                    
                    TextField("", text: $number)
                        .textFieldStyle(RoundedBorderTextFieldStyle())
                    
                    if user != ""{
                        
                        Image("check").foregroundColor(Color.init(.label))
                    }
                    
                }.padding(.trailing)
                                
            }.padding(.trailing).padding(.leading)
            
            
            VStack(alignment: .leading){
                
                Text("Adres").font(.headline).fontWeight(.light).foregroundColor(Color.init(.label).opacity(0.75))
                
                HStack{
                    
                    TextField("", text: $address)
                        .textFieldStyle(RoundedBorderTextFieldStyle())
                    
                    if user != ""{
                        
                        Image("check").foregroundColor(Color.init(.label))
                    }
                    
                }.padding(.trailing)
                                
            }.padding(.trailing).padding(.leading)
            
            Text("Seçilebilecek Ürünler").font(.title2)
            
                
                
                ScrollView (.horizontal) {
                    HStack (spacing:10) {
                        ForEach(self.sell.sells)  { sells in
                            SellItem(item: sells).frame(width:UIScreen.main.bounds.width / 5 * 3, height:300)
                            }
                    }.padding(10)
                }
                
                
                Button(action: {
                   
                }) {
                    Text("Sipariş Et")
                        .fontWeight(.bold)
                        .font(.title3)
                        .padding()
                        .background(Color.red)
                        .foregroundColor(.white)
                        .cornerRadius(15)
                 
                      
                }
                
            }
        }.padding(5)
        .onAppear(){
            self.sell.load()
        }
        
    }
}

struct SellView_Previews: PreviewProvider {
    static var previews: some View {
        SellView(sell:SellData())
    }
}
