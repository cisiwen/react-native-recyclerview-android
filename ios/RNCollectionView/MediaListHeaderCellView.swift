//
//  MediaListHeaderCellView.swift
//  react-native-recyclerview-android
//
//  Created by wweng on 7/4/2023.
//

import Foundation
class MediaListHeaderCellView:UICollectionReusableView {
    public var textView:UILabel!;
    public var checkbox:UIButton!;
    override init(frame: CGRect) {
        
        super.init(frame: frame);
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    public func toggleSelection(){
        if(self.checkbox==nil) {
            self.checkbox=UIButton(frame: CGRect(x:0, y: 0, width: 20, height:20));
            self.addSubview(self.checkbox);
        }

    }
    
    public func setSectionStyle(sectionStyle:SectionHeaderStyleSwift) {
        //self.backgroundColor = //UIColor. sectionStyle.BackgroudColor;
        self.backgroundColor=sectionStyle.BackgroudColor;
        if(sectionStyle.Padding != nil){
            
        }
        if(self.textView != nil){
            self.textView.textColor = sectionStyle.FontColor;
            //self.textView.layoutMargins = UIEdgeInsets(top: 5, left: 125, bottom: 5, right: 105);
            //self.textView.font.pointSize=sectionStyle.FontSize;
            self.textView.bounds =  CGRectInset(self.frame, sectionStyle.Padding!, sectionStyle.Padding!);
            if(sectionStyle.FontSize != nil || sectionStyle.FontWeight != nil){
                var size:CGFloat = sectionStyle.FontSize ?? self.textView.font.pointSize;
                var weight  = sectionStyle.FontWeight! > 500 ? UIFont.Weight.semibold : UIFont.Weight.light;
                var font:UIFont = UIFont.systemFont(ofSize: size, weight: weight);
                self.textView.font = font;
            }
        }
    }
    
    public func setSectionHeader(section:SectionData) {
        if(self.textView==nil) {
            self.textView=UILabel(frame: self.frame);
            
            self.addSubview(self.textView);
        }
        self.backgroundColor=UIColor.red;
        self.textView.text=section.sectionTitle;
    }
}
