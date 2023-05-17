#import <React/RCTViewManager.h>
#import <React/RCTUIManager.h>
#import "RecyclerviewAndroidViewManager.h"
#import "RCTBridge.h"
#import "Utils.h"
#import "react_native_recyclerview_android-Swift.h"


@implementation RecyclerviewAndroidViewManager

RCT_EXPORT_MODULE(RecyclerviewAndroidView)

RCT_EXPORT_VIEW_PROPERTY(onPageSelected, RCTDirectEventBlock)

RNCollectionViewController *collectionViewController;
- (UICollectionView *)view
{
    
    //UICollectionViewFlowLayout* flowLayout = [[UICollectionViewFlowLayout alloc] init];
    //flowLayout.itemSize = CGSizeMake(100, 100);
    CGRect aRect = CGRectMake(0, 0, 100, 100);
    //[flowLayout setScrollDirection:UICollectionViewScrollDirectionHorizontal];
    //UICollectionView *_view = [[UICollectionView alloc] initWithFrame:aRect collectionViewLayout:flowLayout];
    //[_view registerClass:[UICollectionViewCell class] forCellWithReuseIdentifier:@"cell"];
    
    //RNCollectioView *cView=[[RNCollectioView alloc] init];
    //NSLog(@"hello world from %@",cView.getName);
    collectionViewController=[[RNCollectionViewController alloc] initWithFrame:aRect onLongPress:^(NSString *result, NSInteger count) {
        NSLog(@"Result: %@", result);
        NSLog(@"Count: %ld", (long)count);
    }];
    UICollectionView *_view= collectionViewController.collectionView;
    return _view;
}

RCT_CUSTOM_VIEW_PROPERTY(color, NSString, UICollectionView)
{
  [view setBackgroundColor: [Utils hexStringToColor:json]];
}

RCT_CUSTOM_VIEW_PROPERTY(dataSourceString, NSString, UICollectionView)
{
    //NSString * dataSourceString = [[NSString alloc] initWithUTF8String: newViewProps.dataSourceString.c_str()];
    [collectionViewController setDataSourceStringWithDataSource: json];
}

@end
