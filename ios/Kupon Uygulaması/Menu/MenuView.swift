//
//  MenuView.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 18.03.2021.
//

import SwiftUI

struct BarView: View {
    @Binding var place: String
    @Binding var head: String
    @Binding var menu: Bool
    @Binding var back: Bool
    @Binding var homePlace: String
    @Binding var edit: Bool
    @Binding var showShareSheet : Bool
    
    var body: some View {
        
        GeometryReader{ geo in
            
                    
            HStack  {
                
                    Text("100P")
                            .fontWeight(.bold)
                            .font(.headline)
                        .frame(width: geo.size.width/5*1.2)
                        .padding(.top , 7)
                        .padding(.bottom , 7)
                        .background(Color("Renk3"))
                            .foregroundColor(.black)
                
                        .cornerRadius(10)
                        .overlay(RoundedRectangle(cornerRadius: 10)
                                    .stroke(Color("Renk4"), lineWidth: 2))
                        .shadow(radius: 10)

                Button(action: {
                    self.place="sell"
                    self.menu=true
                    self.back=false
                    self.edit=false
                    self.head="Sipariş Oluştur"
                    
                }) {
                    Text("PUANI HARCA")
                        .fontWeight(.bold)
                        .font(.headline)
                        .frame(width: geo.size.width/5*3.5)
                        .padding(.top , 7)
                        .padding(.bottom , 7)
                        .background(Color("Renk4"))
                        .foregroundColor(.white)
                        .cornerRadius(10)

                }

            }.padding(5)
                
            
        }
    }
}


struct ButtonItemView: View {
    @Binding var place: String
    @Binding var head: String
    @Binding var menu: Bool
    @Binding var back: Bool
    @Binding var homePlace: String
    @Binding var edit: Bool
    @Binding var showShareSheet : Bool
    var name: String
    
    var body: some View {
        
       
        Button(action: {
            if name == "Ürünlerim" {
                    self.place="product"
                self.edit=false
                self.head=" ÜRÜNLERİM"
                    self.menu=true
                    self.back=true
                 
               
            }else if name == "Satın Aldıklarım" {
                self.place="sales"
                self.head="SATIN ALDIKLARIM"
                self.menu=true
                self.back=false
                self.edit=true
            }
            else if name == "Kategorilerim" {
                self.place="category"
                self.menu=true
                self.back=false
                self.edit=true
                self.head="KATEGORİLERİM"
            }
            else if name == "Beğendiklerim" {
                self.place="like"
                self.menu=true
                self.back=false
                self.edit=true
                self.head="BEĞENDİKLERİM"
            }
            else if name == "Aradıklarım" {
                self.place="search"
                self.menu=true
                self.back=false
                self.edit=true
                self.head="ARADIKLARIM"
            }
            else if name == "Uygulamayı Paylaş" {
                self.place="share"
                
                self.showShareSheet = true
            }
            else if name == "Çıkış" {
                UserDefaults.standard.setValue(true, forKey: "automaticLogin")

                self.place="exit"

                self.homePlace="login"
            }
            
            
            
        }) {
        
            VStack {
                
                HStack {
                  Text(name).foregroundColor(Color.black) .font(.system(size: 18))
                    .minimumScaleFactor(0.0001)
                    .lineLimit(1)
                    Spacer()
                    Image("chevron.right").accentColor(.black)
                }
                Divider()
                
            }
            
        }.padding(.leading,10)
        .padding(.trailing,10)
        
         
    }
}



struct Item: View {

    var body: some View {
        
            VStack (alignment:.center){
            Text("Cenk Karaboa").font(.headline)
        
            Text("Cenk Karaboa@gmail.com").font(.headline)
               
            }
            
         
}
}


struct MenuView: View {
    @Binding var place: String
    @Binding var head: String
    @Binding var menu: Bool
    @Binding var back: Bool
    @Binding var homePlace: String
    @Binding var edit: Bool
    @Binding var showShareSheet : Bool

    

    var body: some View {

       
        VStack{
      
                Item()
                
          
             Spacer()
            
                let menu = ["Ürünlerim","Satın Aldıklarım","Kategorilerim","Beğendiklerim","Aradıklarım","Uygulamayı Paylaş","Çıkış"]
               
            LazyVGrid(columns: gridLayoutOne, content: {

            ForEach (menu, id: \.self) { i in
                ButtonItemView(place: $place,head: $head,menu:$menu,back:$back, homePlace:$homePlace,edit:$edit,showShareSheet:$showShareSheet ,name :i)      .previewLayout(.sizeThatFits)

                
                    
                }
              
            })
            
        
            
              
            BarView(place: $place,head: $head,menu:$menu,back:$back, homePlace:$homePlace,edit:$edit,showShareSheet:$showShareSheet)

                
        }
        
        
       
    }
}

struct MenuView_Previews: PreviewProvider {
    static var previews: some View {
        MenuView(place: Binding.constant(""),head:Binding.constant(""),menu: Binding.constant(false),back: Binding.constant(false),homePlace:Binding.constant(""), edit: Binding.constant(false),showShareSheet: Binding.constant(false))
    }
}


struct ShareSheet: UIViewControllerRepresentable {
    typealias Callback = (_ activityType: UIActivity.ActivityType?, _ completed: Bool, _ returnedItems: [Any]?, _ error: Error?) -> Void
    
    let activityItems: [Any]
    let applicationActivities: [UIActivity]? = nil
    let excludedActivityTypes: [UIActivity.ActivityType]? = nil
    let callback: Callback? = nil
    
    func makeUIViewController(context: Context) -> UIActivityViewController {
        let controller = UIActivityViewController(
            activityItems: activityItems,
            applicationActivities: applicationActivities)
        controller.excludedActivityTypes = excludedActivityTypes
        controller.completionWithItemsHandler = callback
        return controller
    }
    
    func updateUIViewController(_ uiViewController: UIActivityViewController, context: Context) {
        // nothing to do here
    }
}
