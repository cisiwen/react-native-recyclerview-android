#import <React/RCTViewManager.h>
#import <React/RCTUIManager.h>
#import "RCTBridge.h"
#import "Utils.h"

@interface RecyclerviewAndroidViewManager : RCTViewManager
@end

@implementation RecyclerviewAndroidViewManager

RCT_EXPORT_MODULE(RecyclerviewAndroidView)

- (UICollectionView *)view
{
  return [[UICollectionView alloc] init];
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
