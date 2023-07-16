//
//  ZoomableImageView.swift
//  react-native-recyclerview-android
//
//  Created by wweng on 7/7/2023.
//

import Foundation
import React
@objc(ZoomableImageViewManager)
class ZoomableImageViewManager:RCTViewManager  {
    
    override func view() -> UIView! {
        
        let aRect = CGRect(x:0, y:0, width: 100, height: 100);
        
        let zoomCtrl = ZoomableImageViewController(frame: aRect);
        return zoomCtrl.view;
    }
    
    
   
    override static func requiresMainQueueSetup() -> Bool {
        return true
    }
}
