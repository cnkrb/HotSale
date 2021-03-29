//
//  MainView.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 17.03.2021.
//

import SwiftUI
import Alamofire

enum ActiveSheet {
   case first, second
   var id: Int {
      hashValue
   }
}


struct MainView: View {
    @Binding var homePlace: String
    @State var editText: String = ""
    @State var head: String = " ÜRÜNLERİM"
    @State var place: String = "product"
    @State var menu : Bool = true
    @State var back : Bool = true
    @State var edit : Bool = false
    @State var showShareSheet : Bool = false
    @ObservedObject var search = SearchData()

    @State var exit : Bool = false
    
    @State var showImagePicker: Bool = false
    @State var image: Image? = nil
    
    @State var showSharProduct : Bool = false

    @State var note : String = ""

    var body: some View {
        // self.homePlace = "login"
     
        
        GeometryReader {geometry in
            
            VStack {
                
                    ZStack {

                      if (place == "like"){
                            Color("Gray")
                        }
                   
                      
                        else if (place == "sales"){
                            Color("Gray")

                        }
                        
                        else if (place == "product"){
                            Color("Gray")

                        }
                        
                        
                        ToolbarView().clipShape(ShapeView(yOffset: 30)).ignoresSafeArea()
                           
                        VStack {
                            
                            HStack{
                            
                            Button(action: {
                                self.place="profile"
                                self.head="PROFİLİM"
                                self.back=false
                                self.edit=false
                                self.editText=""
                                self.menu=false
                                    }) {
                                        Image("line.horizontal.3")
                                            .resizable().accentColor(.black)
                                                .frame(width: 20, height: 20) .opacity(menu ? 1 : 0)
                            }.padding(10)
                            
                            Spacer()
                            
                            
                            Text(self.head)
                                .fontWeight(.bold)
                                .font(.title3)
                                .foregroundColor(.white)
                        
                            Spacer()
                            
                            Button(action: {
                                      print("button pressed")

                                    }) {
                                Image("arrow.left").resizable().accentColor(.black)
                                    .frame(width:20, height: 20) .opacity(back ? 1 : 0)
                            }.padding(10)
                                 
                            
                        }.frame(height: 50)
                            
                            TextField("Aranacak kelime yazın", text: $editText, onCommit: {
                                if (place == "search"){
                                   
                                    ekle()
                                   
                               }
                                print(self.editText)
                            })  .textFieldStyle(RoundedBorderTextFieldStyle())
                            .frame(width: geometry.size.width/4*3,alignment: .center)
                            .opacity(edit ? 1 : 0)
                            .cornerRadius(15)
                       
                            
                            
                        }
                      
                    
                    }.frame(width: geometry.size.width, height:80)
                
          
             
                  
                   
                if(place == "product"){
                    ProductView(card:CardData(),showShareSheet:$showShareSheet,note:$note,showSharProduct:$showSharProduct)
                   // self.homePlace="login"
                }else if (place == "profile"){
                    
                    
                    MenuView(place: $place,head: $head,menu:$menu,back:$back, homePlace:$homePlace,edit:$edit,showShareSheet:$showShareSheet)
                }else if (place == "sales"){
                    SalesView(sales: SalesData(),editText: $editText,showImagePicker:$showImagePicker,image:$image,showShareSheet:$showShareSheet)
                }else if (place == "like"){
                    LikeView(like:LikeData(),editText: $editText)
                }
                else if (place == "category"){
                    CategoryView(category : CategoryData(),editText : $editText)
                }
                else if (place == "share"){
                    MenuView(place: $place,head: $head,menu:$menu,back:$back, homePlace:$homePlace,edit:$edit,showShareSheet:$showShareSheet)
                }
                else if (place == "search"){
                    SearchView(search: self.search)
                }
                else if (place == "sell"){
                    SellView(sell:SellData())
                }
            
                     

                    
             
                
                
               
              
                
            }
           
        
        }
        .sheet(isPresented: $showShareSheet) {
            if self.showImagePicker  {
                
                ImagePicker(sourceType: .photoLibrary) { image in
                       self.image = Image(uiImage: image)
                   }

            }else if self.showSharProduct {
                    ShareSheet(activityItems: [self.note])


            }else {
                ShareSheet(activityItems: ["uygulamayı indir kaaydolreferans opuan kzanmak"])

            }

        }
      
       
    }
    
    func ekle() {
        
        //    Call<DefaultResponse> searchSave(@Field("page") String page, @Field("guid") String guid, @Field("search") String search);

        let ID = UserDefaults.standard.object(forKey: "ID")
        print(ID!)
        let parametreler:Parameters = ["page":"search","guid":ID!,"search":self.editText]
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
           
            
            if let data  = response.data {
                
                do {
                    
                    let cevap = try JSONDecoder().decode(Response.self, from: data)
                    
                   // if let kisiListesi = cevap.bilgiler {
                        
                            
                    print("Kisi id  : \(cevap.message!)")
                    if cevap.result! {
                        print("buradaaaaaaa")
                        self.editText=""
                        self.search.load()
                        
                    }
                    
                        
                    
                        
                   // }
                }catch{
                    print(error.localizedDescription)
                }
                
            }
            
             
        }
    }
}

struct MainView_Previews: PreviewProvider {
    static var previews: some View {
        MainView(homePlace: Binding.constant("0"),search: SearchData())
    }
}
