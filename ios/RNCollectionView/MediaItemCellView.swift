//
//  MediaItemCellView.swift
//  react-native-recyclerview-android
//
//  Created by wweng on 1/4/2023.
//

import SDWebImagePhotosPlugin
import SDWebImage
import Foundation
import M13Checkbox
class MediaItemCellView:UICollectionViewCell {
    private var imageView: UIImageView!
    public var checkbox:M13Checkbox!;
    public var indexPath:IndexPath?;
    public var mediaItem:MediaItem?;
    override init(frame: CGRect) {
        super.init(frame: frame);
    }
    
    
    public func toggleSelection(){
        if(self.checkbox==nil) {
            self.checkbox=M13Checkbox(frame: CGRect(x:8, y: 8, width: 25, height:25));
            self.checkbox.boxType = .circle;
            self.checkbox.secondaryTintColor = UIColor.white.withAlphaComponent(0.90);
            self.checkbox.secondaryCheckmarkTintColor = UIColor.black;
            self.checkbox.tintColor = UIColor.white.withAlphaComponent(0.90);
            self.checkbox.boxLineWidth = 1.5;
            self.checkbox.backgroundColor = UIColor.black.withAlphaComponent(0.5);
            //self.checkbox.tintColor = UIColor.black;
            self.checkbox.stateChangeAnimation = .fill;
            //checkbox.setTitle("TEST", for: UIControl.State.normal);
            self.addSubview(self.checkbox);
        }
        if(RNGlobalState.isSelectionMode){
            self.bringSubviewToFront(self.checkbox);
        }
        else
        {
            self.sendSubviewToBack(self.checkbox);
        }

    }
    func setMediaItem(mediaItem:MediaItem) {
        self.clipsToBounds=true;
        mediaItem.mediaItemCellView = self;
        self.mediaItem = mediaItem;
        if(imageView==nil){
            imageView = UIImageView(frame: CGRect(x:0, y:0, width:self.frame.size.width, height:self.frame.size.height));
            imageView.backgroundColor=UIColor.green;
            imageView.clipsToBounds = true;
            imageView.contentMode=ContentMode.scaleAspectFill;
            self.addSubview(imageView);
        }
        //let manager = SDWebImageManager(cache: SDImageCache.shared, loader: SDImagePhotosLoader.shared);
        imageView.sd_setImage(with: URL(string: mediaItem.uri));
        //self.toggleSelection();
        //imageView.contentMode=ContentMode.scaleAspectFill;
    }
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
