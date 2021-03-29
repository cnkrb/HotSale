//
//  SearchView.swift
//  Kupon Uygulaması
//
//  Created by Cenk Karaboa on 18.03.2021.
//

import SwiftUI
import Alamofire
import SwiftyJSON

struct SearchContent: View {
    @ObservedObject var search = SearchData()

    var name: String
    var id: String
    var body: some View {
        
            HStack{
                Text(name).lineLimit(1).font(.system(size: 14)).padding()
                
                Spacer()
                
                Button(action: {
                    delete(id: id)
                   
                }) {
                  
                    Image("xmark").resizable()
                        .foregroundColor(.black)
                        .frame(width: 25   , height: 25)
                        .padding(.trailing,10)
                      
                        
                }
            }
            .cornerRadius(10)
            .overlay(RoundedRectangle(cornerRadius: 10)
                        .stroke(Color.black, lineWidth: 2))
            .shadow(radius: 10)
            
            
        
        
    }
    func delete (id:String) {
        let ID = UserDefaults.standard.object(forKey: "ID")
        print(ID!)
        let parametreler:Parameters = ["page":"search","guid":ID!,"ID":id]
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
           
            
            if let data  = response.data {
                
                do {
                    
                    let cevap = try JSONDecoder().decode(Response.self, from: data)
                    
                   // if let kisiListesi = cevap.bilgiler {
                        
                            
                    print("Kisi id  : \(cevap.message!)")
                    if cevap.result! {
                        print("buradaaaaaaa")
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

struct SearchView: View {

    @ObservedObject var search = SearchData()
    
    var body: some View {
        
        VStack {
            
            LazyVGrid(columns: gridLayout, spacing: 10, content: {
             
                  // This line does not update the first row's text.
                    ForEach(self.search.searchs)  { searchh in
                        SearchContent(search: self.search,name: searchh.name!,id: searchh.id!)
                            
                            .padding(5)
                        }
                 
            }) //: GRID
            .padding(10)
            
          
        }
   
        .onAppear() {
            self.search.load()
        }
    
        
        
    }
    
    func yenile() {
        self.search.load()
    }
    
     
    
     func loadSearch(){
        
        let ID = UserDefaults.standard.object(forKey: "ID")
        print(ID!)
        let parametreler:Parameters = ["page":"search","guid":ID!,"list":""]
        
        AF.request("https://urun.phpscript.info",method: .post,parameters: parametreler).responseJSON { response in
           
            
          //  if let data  = response.data {
                
              
                
          //  }
            
            
        
             
        }
    }
    
}

struct SearchView_Previews: PreviewProvider {
    static var previews: some View {
        SearchView(search: SearchData())
    }
}
