#import <React/RCTEventDispatcher.h>
#import <React/RCTComponent.h>

@interface RecyclerviewAndroidViewManager : RCTViewManager
@property(nonatomic, copy) RCTDirectEventBlock onPageSelected;
@end
