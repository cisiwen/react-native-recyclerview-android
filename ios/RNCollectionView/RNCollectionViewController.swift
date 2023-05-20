//
//  RNCollectionViewController.swift
//  react-native-recyclerview-android
//
//  Created by wweng on 1/4/2023.
//

import Foundation
@objc(RNCollectionViewController)
public class RNCollectionViewController:UICollectionViewController,UICollectionViewDelegateFlowLayout,UIGestureRecognizerDelegate {
    
    
    public var onLongPressed:(String, Int) -> Void;
    private var data:[SectionData];
    @objc public init(frame:CGRect,onLongPress: @escaping (String, Int) -> Void) {
        let layout = UICollectionViewFlowLayout();
        layout.scrollDirection = .vertical;
        layout.estimatedItemSize = .zero;
        data=Array();
        self.onLongPressed=onLongPress;
        super.init(collectionViewLayout: layout);
        layout.headerReferenceSize = CGSize(width: self.collectionView.frame.size.width, height: 20);
        collectionView.frame = frame;
        collectionView.register(MediaItemCellView.self, forCellWithReuseIdentifier: "\(MediaItemCellView.self)");
        collectionView.register(MediaListHeaderCellView.self, forSupplementaryViewOfKind: UICollectionView.elementKindSectionHeader, withReuseIdentifier: "\(MediaListHeaderCellView.self)")
        setupLongGestureRecognizerOnCollection();
        
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
            data = try jsonDecoder.decode([SectionData].self, from: dataSource.data(using: .utf8)!);
            
            var totalIndex = 0;
            var sectionIndex = 0;
            data.forEach { section in
                var index=0;
                section.data.forEach { item in
                    item.sectionIndex = sectionIndex;
                    item.index = index;
                    item.totalIndex = totalIndex;
                    index+=1;
                    totalIndex+=1;
                }
                sectionIndex+=1;
            }
             
            print("sources has ",data.count, Int(Date().timeIntervalSince1970*1000));
            
            //self.collectionView.reloadData();
        }
        catch {
            print(error.localizedDescription);
        }
    }
    
    public override func collectionView(_ collectionView: UICollectionView, didSelectItemAt indexPath: IndexPath) {
        //performSegue(withIdentifier: "showDetail", sender: nil)
    }
    
    /*
     * Section counter
     */
    public override func numberOfSections(in collectionView: UICollectionView) -> Int {
        return data.count;
    }
    
    
    /*
     * Item count in section
     */
    public override func collectionView(_ collectionView: UICollectionView,numberOfItemsInSection section: Int) -> Int {
        return data[section].data.count;
    }
    
    
    public override func collectionView(_ collectionView: UICollectionView, cellForItemAt indexPath: IndexPath) -> UICollectionViewCell {
        let cell = collectionView.dequeueReusableCell(withReuseIdentifier: "\(MediaItemCellView.self)", for: indexPath) as! MediaItemCellView
        
        // Configure the cell
        
        let media = data[indexPath.section].data[indexPath.row];
        //let url = URL(string: media.uri);
        //let data = try? Data(contentsOf: url!)
        //cell.imageView.image = UIImage(data: data!);
        cell.indexPath = indexPath;
        media.sectionIndex = indexPath.section;
        media.index = indexPath.row;
        cell.contentMode=UIView.ContentMode.scaleAspectFill;
        cell.setMediaItem(mediaItem: media);
        cell.backgroundColor=UIColor.red;
        return cell
    }
    
    @objc func handleLongPress(gestureReconizer: UILongPressGestureRecognizer) {
        guard gestureReconizer.state == .began else { return }
        let point = gestureReconizer.location(in: self.collectionView)
        let indexPath = self.collectionView.indexPathForItem(at: point)
        if let index = indexPath{
            print(index.row);
            toggleSelectionMode();
            onLongPressed("testing",index.row);
        }
        else{
            print("Could not find index path")
        }
    }
    
    private func setupLongGestureRecognizerOnCollection() {
        let lpgr = UILongPressGestureRecognizer(target: self, action: #selector(handleLongPress));
        lpgr.minimumPressDuration = 0.5;
        lpgr.delaysTouchesBegan = true;
        lpgr.delegate = self;
        self.collectionView.addGestureRecognizer(lpgr);
    }
    
    public func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, referenceSizeForHeaderInSection section: Int) -> CGSize {
        return CGSize(width: collectionView.frame.width, height: 40)
    }
    
    
    
    public override func collectionView(_ collectionView: UICollectionView,viewForSupplementaryElementOfKind kind: String,at indexPath: IndexPath) -> UICollectionReusableView {
        
        switch(kind) {
        case UICollectionView.elementKindSectionHeader:
            guard
                let headerView = collectionView.dequeueReusableSupplementaryView(
                    ofKind: kind,
                    withReuseIdentifier: "\(MediaListHeaderCellView.self)",
                    for: indexPath
                ) as? MediaListHeaderCellView
            else {
                // 4
                return collectionView.dequeueReusableSupplementaryView(
                    ofKind: kind,
                    withReuseIdentifier: "\(MediaListHeaderCellView.self)",
                    for: indexPath)
            }
            let section = data[indexPath.section];
            headerView.setSectionHeader(section:section);
            return headerView;
            
        default:
            assert(false, "Invalid element type")
        }
    }
    
    public func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, sizeForItemAt indexPath: IndexPath) -> CGSize {
        let collectionWidth = collectionView.frame.width;
        let media = data[indexPath.section].data[indexPath.row];
        if(media.mediaType==MediaType.HEADER) {
            return CGSize(width: collectionWidth, height: 30);
        }
        else
        {
            return CGSize(width: 430 / 4 - 1, height: 430 / 4-1);
        }
    }
    
    
    public override func collectionView(
        _ collectionView: UICollectionView,
        willDisplay cell: UICollectionViewCell,
        forItemAt indexPath: IndexPath
    ) {
        print("willDisplay",indexPath.section, indexPath.row);
        let cellView =  cell as! MediaItemCellView;
        cellView.toggleSelection();
    }
    func toggleSelectionModeByIndexRange(start:Int,end:Int){
        
    }
    
    func toggleSelectionMode(){
        
        RNGlobalState.isSelectionMode = !RNGlobalState.isSelectionMode;
        print("collection view has visible cells ",collectionView.visibleCells.count,collectionView.indexPathsForVisibleItems.count);
        
        for cell in collectionView.visibleCells {
            // Update the cell's appearance
            let cellView =  cell as! MediaItemCellView;
            cellView.toggleSelection();
            //cell.backgroundColor = UIColor.gray
            // You can also update any other properties of the cell, such as its content
        }
        /*
        var start = collectionView.visibleCells.first as! MediaItemCellView;
        var end = collectionView.visibleCells.last as! MediaItemCellView;
        if(start.mediaItem != nil){
            var index = start.mediaItem?.totalIndex;
            for i in stride(from: index!, to: index!-50, by:-1) {
                if(i > -1){
                    
                }
            }
            
        }
         */
    }
    
    public func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumInteritemSpacingForSectionAt section: Int) -> CGFloat {
        return 1
    }
    public func collectionView(_ collectionView: UICollectionView, layout collectionViewLayout: UICollectionViewLayout, minimumLineSpacingForSectionAt section: Int) -> CGFloat {
        return 1
    }
}
