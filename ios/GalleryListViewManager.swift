//
//  GalleryListView.swift
//  react-native-recyclerview-android
//
//  Created by wweng on 20/5/2023.
//
import Foundation
import React
@objc(GalleryListViewManager)
class GalleryListViewManager:RCTViewManager  {
    
    var galleryListView:GalleryListView!=nil;
    override func view() -> UIView! {
        
        let aRect = CGRect(x:0, y:0, width: 100, height: 100);
        galleryListView = GalleryListView(frame: aRect);
        return galleryListView;
    }
    
    override static func requiresMainQueueSetup() -> Bool {
        return true
    }
    
    @objc public func toggleSelectionMode(_ rectTag:NSNumber,selectionMode:Bool)-> Void {
        galleryListView.toggleSelectionMode(selectionMode);
    }
}
