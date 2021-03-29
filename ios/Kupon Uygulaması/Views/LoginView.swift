//
//  LoginView.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 17.03.2021.
//

import SwiftUI
import Alamofire
import SwiftyJSON

struct LoginView: View {
    @Binding var homePlace: String
    @State var user = ""
    @State var pass = ""
    @State var checkState:Bool = false ;
        
    var body: some View {

            
            GeometryReader {geometry in
                VStack{
                
                Text("Üye Girişi")
                    .bold()
                    .frame(width: geometry.size.width, height: geometry.size.height/10,alignment: .leading)
                    .padding(.leading)
                    .font(.system(size: 22))
                    
                VStack(alignment: .leading){
                    
                    Text("Email").font(.headline).fontWeight(.light).foregroundColor(Color.init(.label).opacity(0.75))
                    
                    HStack{
                        
                        TextField("email123@gmail.com", text: $user)
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                        
                        if user != ""{
                            
                            Image("check").foregroundColor(Color.init(.label))
                        }
                        
                    }.padding(.trailing)
                                    
                }.padding(.bottom, 15).padding(.trailing).padding(.leading)
                
                
                VStack(alignment: .leading){
                    
                    Text("Password").font(.headline).fontWeight(.light).foregroundColor(Color.init(.label).opacity(0.75))
                
                        
                    SecureField("*********", text: $pass)  .textFieldStyle(RoundedBorderTextFieldStyle()).padding(.trailing)
                    
               
                }.padding(.bottom, 15).padding(.trailing).padding(.leading)
                
                HStack{
                    
                    Spacer()
                    
                    Button(action: {
                        
                    }) {
                        
                        Text("Şifremi Unuttum ?").foregroundColor(Color.gray.opacity(0.5))
                            .padding(.trailing)
                    }
                }.padding(.horizontal).padding(.top,5)

            
                
                Button(action:
                           {
                               //1. Save state
                               self.checkState = !self.checkState
                               print("State : \(self.checkState)")


                       }) {
                    HStack {

                                  Rectangle()
                                           .fill(self.checkState ? Color.green : Color.red)
                                           .frame(width:20, height:20, alignment: .center)
                                           .cornerRadius(5)

                        Text("Oturumum açık kalsın").foregroundColor(.black)

                    }.padding(.horizontal).padding(.trailing)
                }.frame(width: geometry.size.width, height: geometry.size.height/20,alignment: .trailing)
                .foregroundColor(Color.white)
            
                    
                    
                    Button(action: {
                        if user.isEmpty || pass.isEmpty {
                            
                        }else {
                            print("asdasd")
                            giris(email: user, password: pass)

                       }
                      
                    }) {
                        HStack{
                            HStack {
                                Image("deneme2").resizable()
                                    .font(.title).frame(width: 45, height: 45)
                            }.frame(width: 70, height: 70, alignment: .center)
                            .foregroundColor(.white)
                            .background(Color.red)
                            .clipShape(Circle())
                            .padding(.trailing,15)
                            
                            
                            
                        }.frame(width: geometry.size.width, height: geometry.size.height/7, alignment: .trailing)
                        .padding(.trailing,15)
                    }
                    
                    
                    Text("Sosyal Ağ Üye Girişi")
                        .frame(width: geometry.size.width, height: geometry.size.height/15,alignment: .center)
                        .font(.system(size: 17))
                    
                    HStack{
                        ZStack {
                            Image("google").resizable()
                                .font(.title).frame(width: 45, height: 45)
                        }.frame(width: 70, height: 70, alignment: .center)
                        .foregroundColor(.white)
                        .clipShape(Circle())
                       
                       
                    
                        ZStack {
                            Image("facebook").resizable()
                                .font(.title).frame(width: 45, height: 45)
                        }.frame(width: 70, height: 70, alignment: .center)
                        .foregroundColor(.white)
                        
                        .clipShape(Circle())
                      
                    
                    }
                    
                    
                }
            
                
            }
           
        
       
        }

    
    func giris(email:String,password:String){
        let parametreler:Parameters = ["page":"login","email":email,"password":password]
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
        
                do {
                
                    let cevap = try JSONDecoder().decode(Response.self, from: response.data! )
                    print("message login  : \(String(describing: cevap.message))")
                    
                   
                    if cevap.result!{
                        self.homePlace="main"
                        self.user=""
                        self.pass=""
                        
                        if checkState {
                            UserDefaults.standard.setValue(false, forKey: "automaticLogin")
                          
                        }
                        
                        
                        UserDefaults.standard.setValue(cevap.ID!, forKey: "ID")
                        
                    }else{
                        
                    }
                    

                }catch{
                    print(error.localizedDescription)
                }
                
            }
    }
    
    
    

   
    
    
    

}



 


struct LoginView_Previews: PreviewProvider {
    static var previews: some View {
        LoginView(homePlace: Binding.constant("0"))
    }
}





