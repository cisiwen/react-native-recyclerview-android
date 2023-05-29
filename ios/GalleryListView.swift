//
//  GalleryListView.swift
//  react-native-recyclerview-android
//
//  Created by wweng on 20/5/2023.
//

import Foundation
import React
import SDWebImagePhotosPlugin
import PhotosUI
class GalleryListView:UIView {
    @objc var onLongPressed:RCTDirectEventBlock? = nil
    @objc var dataSourceString: NSString = ""
    private var rnCollectionViewController:RNCollectionViewController?;
    override init(frame:CGRect) {
        super.init(frame: frame);
        
        let options:PHImageRequestOptions  = PHImageRequestOptions();
        options.sd_targetSize = CGSizeMake(500, 500);
        SDImagePhotosLoader.shared.imageRequestOptions = options;
        SDImagePhotosLoader.shared.requestImageAssetOnly = false;
        SDImageLoadersManager.shared.loaders = [SDWebImageDownloader.shared, SDImagePhotosLoader.shared];
        // Replace default manager's loader implementation
        SDWebImageManager.defaultImageLoader = SDImageLoadersManager.shared;
        
       
    }
    
    
    func requestPermission(){
        if #available(iOS 14, *) {
            PHPhotoLibrary.requestAuthorization(for: .readWrite) { [unowned self] (status) in
                DispatchQueue.main.async { [unowned self] in
                    
                }
            }
        } else {
            // Fallback on earlier versions
        }

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
            //requestPermission();
            rnCollectionViewController?.setDataSourceString(dataSource: self.dataSourceString as String);
        }
        
    }
    
    
    
    func addCollectionView(){
        let aRect = CGRectMake(0, 0, 100, 100);
        rnCollectionViewController=RNCollectionViewController(frame: aRect, onLongPress:callback);
        if(rnCollectionViewController != nil){
            self.addSubview((rnCollectionViewController?.collectionView)!);
        }
    }
}
