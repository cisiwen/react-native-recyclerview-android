//
//  ZoomableImageViewController.swift
//  react-native-recyclerview-android
//
//  Created by wweng on 7/7/2023.
//

import Foundation
import SDWebImagePhotosPlugin
import UIKit
import WebKit

extension UIImageView {
    func load(url: URL) {
        DispatchQueue.global().async { [weak self] in
            if let data = try? Data(contentsOf: url) {
                if let image = UIImage(data: data) {
                    DispatchQueue.main.async {
                        self?.image = image
                    }
                }
            }
        }
    }
}
class ZoomableImageViewController:UIViewController {
    public var imageView: UIImageView!
    
    public init(frame:CGRect){
        super.init(nibName: nil, bundle: nil);
        
    }
    
    func setSource(uriString:String){
        //ph://ED7AC36B-A150-4C38-BB8C-B6D696F4F2ED/L0/001/IMG_0005.JPG
        let id = uriString.replacingOccurrences(of: "ph://", with: "");
        var parts = id.split(separator: "/");
        parts.removeLast();
        let identifier: String = parts.joined(separator: "/");
        //let photosURL = NSURL.sd_URL(withAssetLocalIdentifier: identifier) as URL;
        let photoUrl = URL(string:"https://images.unsplash.com/photo-1579353977828-2a4eab540b9a?ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D&auto=format&fit=crop&w=1374&q=80");
        //imageView.sd_setImage(with: photosURL);
        //imageView.setImage
        //imageView.sd_setImage(with: URL(string: "https://asia.olympus-imaging.com/content/000107506.jpg"));
        //addZoombehavior(for: self.imageView,in:parentView, settings:Settings.instaZoomSettings);
        imageView.load(url: photoUrl!);
       
       
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    func loadImages() {
        let aRect = CGRectMake(0, 0, self.view.frame.width, self.view.frame.height/2);
        self.imageView = UIImageView(frame:aRect);
        imageView.clipsToBounds = true;
        imageView.contentMode=UIView.ContentMode.scaleAspectFit;
        self.view.addSubview(imageView);
        setSource(uriString: "ph://ED7AC36B-A150-4C38-BB8C-B6D696F4F2ED/L0/001/IMG_0005.JPG");
    }
    
    override func viewDidLoad() {
        super.viewDidLoad();
        loadImages();
        //let setting = Settings().with(actionOnTapOverlay: Action.dismissOverlay).with(maximumZoomScale: 10);
        //addZoombehavior(for: imageView);
       

    }
}
