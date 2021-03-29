//
//  SigInControllerView.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 17.03.2021.
//

import SwiftUI

struct SigInControllerView: View {
    @State var homePlace: String = "register"
    
    var body: some View {
  
        
       
            if homePlace == "login" {
                let automaticLogin = UserDefaults.standard.bool(forKey: "automaticLogin")
                if !automaticLogin {
                    MainView(homePlace: $homePlace,search: SearchData() )
                }else{
                    LoginView(homePlace: $homePlace);

                }
            }else if homePlace == "register" {
                let automaticLogin = UserDefaults.standard.bool(forKey: "automaticLogin")
                if !automaticLogin {
                        MainView(homePlace: $homePlace,search: SearchData() )
                }else{
                    
                    RegisterView(homePlace: $homePlace)

                }
            }else if homePlace == "main" {
                let automaticLogin = UserDefaults.standard.bool(forKey: "automaticLogin")
                if !automaticLogin {
                    MainView(homePlace: $homePlace,search: SearchData() )
                }else {
                    MainView(homePlace: $homePlace,search: SearchData() )

                }
            }
        
          
        
        
    }
}

struct SigInControllerView_Previews: PreviewProvider {
    static var previews: some View {
        SigInControllerView()
    }
}
