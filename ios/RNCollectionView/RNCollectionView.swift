//
//  RNCollectionView.swift
//  CocoaAsyncSocket
//
//  Created by wweng on 29/3/2023.
//

import Foundation
@objc(RNCollectioView)
public class RNCollectioView:NSObject {
    
    private var collectionView:UICollectionView!;
    public override init(){
        super.init();
        
    }
    @objc public func getName()->String {
        return "Hello from swift";
    }
    
    @objc public func initializeUICollectionView(frame: CGRect)->UICollectionView {
        let layout = UICollectionViewFlowLayout();
        layout.scrollDirection = .vertical;
        collectionView = UICollectionView(frame: frame, collectionViewLayout: layout);
        //collectionView.backgroundColor = .white;
        collectionView.register(UICollectionViewCell.self, forCellWithReuseIdentifier: "cell");
        return collectionView;
    }
    
    @objc public func setDataSourceString(dataSource: String)->Void{
        NSLog("Your name is %@TESTING%lu", self.getName(), Int(Date().timeIntervalSince1970*1000));
        let jsonDecoder = JSONDecoder();
        do {
            let sources = try jsonDecoder.decode([MediaItem].self, from: dataSource.data(using: .utf8)!);
            print("sources has ",sources[0].uri, Int(Date().timeIntervalSince1970*1000));
        }
        catch {
            print(error.localizedDescription);
        }
    }
    
}
