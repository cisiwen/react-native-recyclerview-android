//
//  Media.swift
//  react-native-recyclerview-android
//
//  Created by wweng on 1/4/2023.
//

import Foundation
class MediaType:Codable {
    public static let VIDEO:String = "VIDEO";
    public static let IMAGE:String="IMAGE";
    public static let HEADER:String="HEADER";
}
class MediaItemUIState:Codable{
    public var selected:Bool;
}
class MediaItem:Codable{
    public var contentId:String!;
    public var uri: String!;
    public var width: String!;
    public var height: String!;
    public var contentUri: String!;
    public var mediaType: String!;
    public var index:Int?;
    public var sectionIndex:Int?;
    public var totalIndex:Int?;
    public var mediaItemUIState:MediaItemUIState!;
    @CodableIgnored
    public var mediaItemCellView:MediaItemCellView?
}

class SectionData:Codable {
    public var sectionTitle:String;
    public var sectionId:String;
    public var mediaItemUIState:MediaItemUIState!;
    public var data:Array<MediaItem>;
 
}
class SectionHeaderStyle:Codable {
    public var BackgroudColor:String?;
    public var Padding:CGFloat?;
    public var FontSize:CGFloat?;
    public var FontColor:String?;
    public var FontWeight:CGFloat?;
    
    public required init() {
        
    }
}

class SectionHeaderStyleSwift{
    public var BackgroudColor:UIColor?;
    public var Padding:CGFloat?;
    public var FontSize:CGFloat?;
    public var FontColor:UIColor?;
    public var FontWeight:CGFloat?;
    
    public required init(){}
    public required init(style:SectionHeaderStyle) {
        
        if(style.BackgroudColor != nil){
            self.BackgroudColor = UIColor(hexString: style.BackgroudColor!);
        }
        if(style.FontColor != nil){
            self.FontColor = UIColor(hexString: style.FontColor!);
        }
        if(style.FontSize != nil){
            self.FontSize=style.FontSize;
        }
        if(style.Padding != nil){
            self.Padding=style.Padding;
        }
        if(style.FontWeight != nil){
            self.FontWeight=style.FontWeight;
        }
    }
}
