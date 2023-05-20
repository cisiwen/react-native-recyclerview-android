//
//  GalleryListView.swift
//  react-native-recyclerview-android
//
//  Created by wweng on 20/5/2023.
//

import Foundation
import React
class GalleryListView:UIView {
    @objc var onLongPressed:RCTDirectEventBlock? = nil
    @objc var dataSourceString: NSString = ""
    private var rnCollectionViewController:RNCollectionViewController?;
    override init(frame:CGRect) {
        super.init(frame: frame);
        
       
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func callback(name:String, index:Int) -> Void{
        onLongPressed!(["message": "I am finished", "foo": "bar"]);
    }
    
    
    override func didSetProps(_ changedProps: [String]!) {
        if(changedProps.contains("dataSourceString")){
            if(rnCollectionViewController == nil) {
                addCollectionView();
            }
            rnCollectionViewController?.setDataSourceString(dataSource: self.dataSourceString as String);
        }
        
    }
    
    
    
    func addCollectionView(){
        let aRect = CGRectMake(0, 0, 430, 932);
        rnCollectionViewController=RNCollectionViewController(frame: aRect, onLongPress:callback);
        if(rnCollectionViewController != nil){
            self.addSubview((rnCollectionViewController?.collectionView)!);
        }
    }
}
