//
//  Media.swift
//  react-native-recyclerview-android
//
//  Created by wweng on 1/4/2023.
//

import Foundation
struct MediaItem:Codable{
    public var contentId:String;
    public var uri: String;
    public var width: String;
    public var height: String;
    public var contentUri: String;
    public var mediaType: String;
}
