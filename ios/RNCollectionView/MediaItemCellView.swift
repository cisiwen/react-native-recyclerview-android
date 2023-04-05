//
//  MediaItemCellView.swift
//  react-native-recyclerview-android
//
//  Created by wweng on 1/4/2023.
//

import SDWebImage
import SDWebImagePhotosPlugin
import Foundation
class MediaItemCellView:UICollectionViewCell {
    private var imageView: UIImageView!
    override init(frame: CGRect) {
        super.init(frame: frame);
    }
    
    func setMediaItem(mediaItem:MediaItem) {
        if(imageView==nil){
            imageView = UIImageView(frame: CGRect(x:0, y:0, width:self.frame.size.width, height:self.frame.size.height));
            imageView.backgroundColor=UIColor.green;
            imageView.clipsToBounds = true;
            imageView.contentMode=ContentMode.scaleAspectFill;
            self.addSubview(imageView);             
        }
        //let manager = SDWebImageManager(cache: SDImageCache.shared, loader: SDImagePhotosLoader.shared);
        imageView.sd_setImage(with: URL(string: mediaItem.uri));
        //imageView.contentMode=ContentMode.scaleAspectFill;
    }
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
