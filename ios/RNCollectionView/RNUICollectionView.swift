//
//  RNUICollectionView.swift
//  react-native-recyclerview-android
//
//  Created by wweng on 1/4/2023.
//

import Foundation
@objc(RNUICollectionView)
public class RNUICollectionView:UICollectionView,UICollectionViewDataSource,UICollectionViewDelegate {
    
    @objc public init(frame: CGRect) {
        let layout = UICollectionViewFlowLayout();
        layout.scrollDirection = .vertical;
        super.init(frame: frame, collectionViewLayout: layout);
    }
    
    required init?(coder: NSCoder) {
        fatalError("init(coder:) has not been implemented")
    }
    
    public func getName()->String {
        return "Hello from swift";
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
    
    public func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        return 100;
    }
    
    public func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        
        return MediaItemCellView();
        
    }
}
