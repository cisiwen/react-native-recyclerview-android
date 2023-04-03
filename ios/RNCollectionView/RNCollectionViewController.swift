//
//  RNCollectionViewController.swift
//  react-native-recyclerview-android
//
//  Created by wweng on 1/4/2023.
//

import Foundation
@objc(RNCollectionViewController)
public class RNCollectionViewController:UICollectionViewController,UICollectionViewDelegateFlowLayout {
    
    private var data:[MediaItem];
    @objc public init(frame:CGRect) {
        let layout = UICollectionViewFlowLayout();
        layout.scrollDirection = .vertical;
        data=Array();
        super.init(collectionViewLayout: layout);
        collectionView.register(MediaItemCellView.self, forCellWithReuseIdentifier: "dataCell")
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
            data = try jsonDecoder.decode([MediaItem].self, from: dataSource.data(using: .utf8)!);
            print("sources has ",data[0].uri, Int(Date().timeIntervalSince1970*1000));
            //self.collectionView.reloadData();
        }
        catch {
            print(error.localizedDescription);
        }
    }
    
    public override func collectionView(_ collectionView: UICollectionView, numberOfItemsInSection section: Int) -> Int {
        // #warning Incomplete implementation, return the number of items
        return data.count
    }
    
    public override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "dataCell", for: indexPath) as! MediaItemCellView
        
        // Configure the cell
        
        let media = data[indexPath.row];
        //let url = URL(string: media.uri);
        //let data = try? Data(contentsOf: url!)
        //cell.imageView.image = UIImage(data: data!);
        cell.contentMode=UIView.ContentMode.scaleAspectFill;
        cell.setMediaItem(mediaItem: media);
        cell.backgroundColor=UIColor.red;
        return cell
    }
    
    public override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        //performSegue(withIdentifier: "showDetail", sender: nil)
    }
    
    public func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
            let collectionWidth = collectionView.bounds.width
            return CGSize(width: collectionWidth / 4, height: collectionWidth / 4)
        }
    public func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
            return 0
        }
    public func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
            return 0
        }
}
