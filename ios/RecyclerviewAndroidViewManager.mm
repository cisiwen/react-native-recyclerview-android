#import <React/RCTViewManager.h>
#import <React/RCTUIManager.h>
#import "RCTBridge.h"
#import "Utils.h"
#import "react_native_recyclerview_android-Swift.h"
@interface RecyclerviewAndroidViewManager : RCTViewManager
@end

@implementation RecyclerviewAndroidViewManager

RCT_EXPORT_MODULE(RecyclerviewAndroidView)

- (UICollectionView *)view
{
    
    UICollectionViewFlowLayout* flowLayout = [[UICollectionViewFlowLayout alloc] init];
    flowLayout.itemSize = CGSizeMake(100, 100);
    CGRect aRect = CGRectMake(0, 0, 100, 100);
    [flowLayout setScrollDirection:UICollectionViewScrollDirectionHorizontal];
    UICollectionView *_view = [[UICollectionView alloc] initWithFrame:aRect collectionViewLayout:flowLayout];
    [_view registerClass:[UICollectionViewCell class] forCellWithReuseIdentifier:@"cell"];
    
    RNCollectioView *cView=[[RNCollectioView alloc] init];
    NSLog(@"hello world from %@",cView.getName);
    return _view;
}

RCT_CUSTOM_VIEW_PROPERTY(color, NSString, UICollectionView)
{
  [view setBackgroundColor: [Utils hexStringToColor:json]];
}

RCT_CUSTOM_VIEW_PROPERTY(dataSourceString, NSString, UICollectionView)
{
  [view setBackgroundColor: [Utils hexStringToColor:json]];
}

@end
