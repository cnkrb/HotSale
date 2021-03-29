//
//  RegisterView.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 17.03.2021.
//

import SwiftUI
import Alamofire
import SwiftyJSON

struct RegisterView: View {
    @Binding var homePlace: String
    
    @State var user = ""
    @State var emailref = ""
    @State var gender = ""
    @State var pass = ""
    @State var checkState:Bool = false ;
    @State var showView:Bool = false;
    @State var selectedDate = Date()

    @State private var actionSheetGorunsunMu = false
    
    var body: some View {
        

        GeometryReader {geometry in
            VStack{
            
            Text("Üye Kayıt")
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
                                
            }.padding(.trailing).padding(.leading)
            
            
            VStack(alignment: .leading){
                
                Text("Password").font(.headline).fontWeight(.light).foregroundColor(Color.init(.label).opacity(0.75))
            
                    
                SecureField("*********", text: $pass)  .textFieldStyle(RoundedBorderTextFieldStyle()).padding(.trailing)
                
           
            }.padding(.bottom, 10).padding(.trailing).padding(.leading)
            
        
                VStack(alignment: .leading){
                    
                    Text("Cinsiyet Seçimi").font(.headline).fontWeight(.light).foregroundColor(Color.init(.label).opacity(0.75))
                        .padding(.leading)
                    
                    
                        Button(action:{
                            self.actionSheetGorunsunMu = true
                        }){
                            Text("Cinsiyet seçmek için tıklayınız").font(.system(size: 18))
                                .accentColor(.black)
                        }.actionSheet(isPresented: $actionSheetGorunsunMu){
                            
                            ActionSheet(title: Text("Cinsiyet Seçimi")
                                , buttons: [
                                    .default(Text("Kadın")) {
                                        self.gender="Kadın"
                                    },.destructive(Text("Erkek")){
                                        self.gender="Erkek"
                                    }
                            ])
                            
                            
                        }.padding(.leading).padding(.top,5)
                        
                   
                                    
                }.frame(width:  geometry.size.width, height:  geometry.size.height/15, alignment: .leading).padding(.bottom, 10)
        
                
                

                
                VStack(alignment: .leading){
                    
                    Text("Doğum Tarihi").font(.headline).fontWeight(.light).foregroundColor(Color.init(.label).opacity(0.75))
                        .padding(.leading)
                        .padding(.top,5)
                    
                    
                
                        DatePicker("", selection: $selectedDate, displayedComponents: .date)
                            .fixedSize().padding(.leading,5)
                    
                                    
                }.frame(width:  geometry.size.width, height:  geometry.size.height/10, alignment: .leading).padding(.bottom, 15)
        
                
        
                VStack(alignment: .leading){
                    
                    Text("Referans Eposta").font(.headline).fontWeight(.light).foregroundColor(Color.init(.label).opacity(0.75))
                    
                    HStack{
                        
                        TextField("email123@gmail.com", text: $emailref)
                            .textFieldStyle(RoundedBorderTextFieldStyle())
                        
                        if user != ""{
                            
                            Image("check").foregroundColor(Color.init(.label))
                        }
                        
                    }.padding(.trailing)
                                    
                }.padding(.bottom, 15).padding(.trailing).padding(.leading)
        
                
                Button(action: {
                    if user.isEmpty || pass.isEmpty {

                        
                    }else {
                        let formatter1 = DateFormatter()
                        formatter1.dateStyle = .short

                        let datee = formatter1.string(from: selectedDate)
                        kayıtOl(email: user, password: pass, gender: gender, emailRef: emailref, date: datee)
                    }
                }) {
                    HStack {
                        Image("deneme2").resizable()
                            .font(.title).frame(width: 45, height: 45)
                    }.frame(width: 70, height: 70, alignment: .center)
                    .foregroundColor(.white)
                    .background(Color.red)
                    .clipShape(Circle())
                    .padding(.trailing,15)
                    
                }.frame(width: geometry.size.width, height: geometry.size.height/8, alignment: .trailing)
                .padding(.trailing,15)
                  
                Button(action: {
                    self.homePlace="login"
                }, label: {
                    Text("Hesabın var mı? Giriş Yap").accentColor(.black)
                })
                            
                           
            }.frame(width: geometry.size.width, height: geometry.size.height)
        }
        
    }
    
    func kayıtOl(email:String,password:String,gender:String,emailRef:String,date:String){
        let parametreler:Parameters = ["page":"register","email":email,"gender":gender,"birthday":(date),"emailRef":emailRef,"password":password,"lang":"tr"]
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
        
                do {
                
                    let cevap = try JSONDecoder().decode(Response.self, from: response.data! )
                    print("message login  : \(String(describing: cevap.message))")
                    
                    if cevap.result!{
                        self.homePlace="main"
                        self.user=""
                        self.pass=""
                        self.emailref=""
                        self.selectedDate = Date()
                        self.gender=""
                        
                        UserDefaults.standard.setValue(false, forKey: "automaticLogin")
                        UserDefaults.standard.setValue(cevap.ID!, forKey: "ID")
                    }else{
                        
                    }

                }catch{
                    print(error.localizedDescription)
                    if error.localizedDescription == "The data couldn’t be read because it isn’t in the correct format." {
                        
                        do {
                        
                            let cevap = try JSONDecoder().decode(Register.self, from: response.data! )
                            print("message login  : \(String(describing: cevap.message))")
                           
                        }catch{
                           
                        }
                    }
                }
                
            }
    
    }
}

struct RegisterView_Previews: PreviewProvider {
    static var previews: some View {
        RegisterView(homePlace: Binding.constant("0"))
    }
}
