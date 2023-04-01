//
//  MediaItemCellView.swift
//  react-native-recyclerview-android
//
//  Created by wweng on 1/4/2023.
//

import Foundation
class MediaItemCellView:UICollectionViewCell {
    @IBOutlet weak var imageView: UIImageView!
    override init(frame: CGRect) {
        super.init(frame: frame)
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
}
