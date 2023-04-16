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
    
    public func setSectionHeader(section:SectionData) {
        if(self.textView==nil) {
            self.textView=UILabel(frame: self.frame);
            self.addSubview(self.textView);
        }
        self.backgroundColor=UIColor.red;
        self.textView.text=section.sectionTitle;
    }
}
